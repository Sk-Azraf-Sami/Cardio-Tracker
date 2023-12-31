package com.example.cardiotracker;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The AddRecord class represents an activity that allows users to add a record with heart rate, systolic pressure,
 * diastolic pressure, and optional comment to a Firebase database. It validates the input data and stores it under
 * the current user's ID in the database.
 */
public class AddRecord extends AppCompatActivity {
    public EditText editHeartRate, editSysPressure, editDiaPressure, editComment;
    public Button btnAdd;
    private Button clear;
    public DatabaseReference databaseReference;

    /**
     * Called when the activity is starting. This method sets up the activity layout and initializes the required views
     * and buttons. It also sets the click listeners for the 'Add' and 'Clear' buttons.
     *
     * @param savedInstanceState a Bundle containing the saved state of the activity, or null if there is no saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        databaseReference = databaseReference = FirebaseDatabase.getInstance().getReference();

        editHeartRate = findViewById(R.id.editHeartRate);
        editSysPressure = findViewById(R.id.editSystolicPressure);
        editDiaPressure = findViewById(R.id.editDiastolicPressure);
        editComment = findViewById(R.id.editAddComment);
        btnAdd = findViewById(R.id.btnAdd);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInformation();
            }
        });

        clear = findViewById(R.id.btnClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    /**
     * Validates the input data and adds the record to the database under the current user's ID. It retrieves the heart rate,
     * systolic pressure, diastolic pressure, comment, current date, and current time. It performs input validation and
     * generates a unique ID for the record. Then, it creates a new node in the database and sets the values for the record.
     * Finally, it displays a toast message indicating the success or failure of the data addition.
     */
    public void addInformation() {
        String heartRate = editHeartRate.getText().toString().trim();
        String sysPressure = editSysPressure.getText().toString().trim();
        String diaPressure = editDiaPressure.getText().toString().trim();
        String comment = editComment.getText().toString().trim();
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault()).format(new Date());

        if (TextUtils.isEmpty(heartRate)) {
            editHeartRate.setError("Heart Rate cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(sysPressure)) {
            editSysPressure.setError("Systolic Pressure cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(diaPressure)) {
            editDiaPressure.setError("Diastolic Pressure cannot be empty");
            return;
        }

        // Comment can be empty

        // Record validation
        int iHeartRate = Integer.parseInt(heartRate);
        int iSysPressure = Integer.parseInt(sysPressure);
        int iDiaPressure = Integer.parseInt(diaPressure);

        if (iHeartRate < 0) {
            editHeartRate.setError("Heart Rate cannot be negative");
            return;
        }

        if (iSysPressure < 0) {
            editSysPressure.setError("Pressure cannot be negative");
            return;
        }

        if (iDiaPressure < 0) {
            editDiaPressure.setError("Pressure cannot be negative");
            return;
        }

        // Get the current user's unique ID from Firebase Authentication
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Get the reference for the current user's data in the database
        DatabaseReference userRef = databaseReference.child("Users").child(userId);

        // Generate a unique ID for the record
        String recordId = userRef.push().getKey();

        // Create a new node under the current user's ID and set the values
        DatabaseReference recordRef = userRef.child(recordId);
        recordRef.child("HeartRate").setValue(heartRate);
        recordRef.child("SysPressure").setValue(sysPressure);
        recordRef.child("DiaPressure").setValue(diaPressure);
        recordRef.child("Comment").setValue(comment);
        recordRef.child("SystemDate").setValue(currentDate);
        recordRef.child("SystemTime").setValue(currentTime)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AddRecord.this, "Data added successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddRecord.this, "Failed to add data!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * Clears the input fields for heart rate, systolic pressure, diastolic pressure, and comment.
     */
    private void clearFields() {
        editHeartRate.setText("");
        editSysPressure.setText("");
        editDiaPressure.setText("");
        editComment.setText("");
    }
}
