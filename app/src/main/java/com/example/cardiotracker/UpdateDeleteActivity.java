package com.example.cardiotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateDeleteActivity extends AppCompatActivity {

    private EditText commentEditText, diaPressureEditText, heartRateEditText, sysPressureEditText,
            systemDateEditText, systemTimeEditText;
    private Button updateButton, deleteButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Get the reference for the current user's data in the database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        commentEditText = findViewById(R.id.commentEditText);
        diaPressureEditText = findViewById(R.id.diaPressureEditText);
        heartRateEditText = findViewById(R.id.heartRateEditText);
        sysPressureEditText = findViewById(R.id.sysPressureEditText);
       // systemDateEditText = findViewById(R.id.systemDateEditText);
       // systemTimeEditText = findViewById(R.id.systemTimeEditText);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
        }

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });
    }

    private void updateData() {
        String comment = commentEditText.getText().toString().trim();
        String diaPressure = diaPressureEditText.getText().toString().trim();
        String heartRate = heartRateEditText.getText().toString().trim();
        String sysPressure = sysPressureEditText.getText().toString().trim();
        //String systemDate = systemDateEditText.getText().toString().trim();
        //String systemTime = systemTimeEditText.getText().toString().trim();

        // Update the data fields in the database using the key
        databaseReference.child(key).child("Comment").setValue(comment);
        databaseReference.child(key).child("DiaPressure").setValue(diaPressure);
        databaseReference.child(key).child("HeartRate").setValue(heartRate);
        databaseReference.child(key).child("SysPressure").setValue(sysPressure);
        //databaseReference.child(key).child("SystemDate").setValue(systemDate);
        //databaseReference.child(key).child("SystemTime").setValue(systemTime);

        Toast.makeText(this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
    }

    private void deleteData() {
        // Remove the data from the database using the key
        databaseReference.child(key).removeValue();

        Toast.makeText(this, "Data deleted successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Return to the previous activity
    }
}
