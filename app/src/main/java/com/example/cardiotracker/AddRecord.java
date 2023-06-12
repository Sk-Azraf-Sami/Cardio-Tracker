package com.example.cardiotracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddRecord extends AppCompatActivity {
    private EditText editHeartRate, editSysPressure, editDiaPressure, editComment;
    private Button btnAdd;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

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
    }
    private void addInformation() {
        String heartRate = editHeartRate.getText().toString().trim();
        String sysPressure = editSysPressure.getText().toString().trim();
        String diaPressure = editDiaPressure.getText().toString().trim();
        String comment = editComment.getText().toString().trim();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

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

        //comment can be empty

        //record validation
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

        //data insert under record id
        String recordId = databaseReference.child(userId).push().getKey();

        // Create a new node under the current user's ID and set the values
        DatabaseReference userRef = databaseReference.child(recordId);
        userRef.child("HeartRate").setValue(heartRate);
        userRef.child("SysPressure").setValue(sysPressure);
        userRef.child("DiaPressure").setValue(diaPressure);
        userRef.child("Comment").setValue(comment);

        userRef.child("SystemDate").setValue(currentDate);
        userRef.child("SystemTime").setValue(currentTime)

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
}