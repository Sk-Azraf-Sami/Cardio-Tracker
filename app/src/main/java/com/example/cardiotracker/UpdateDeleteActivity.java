package com.example.cardiotracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
/**
 * This activity allows the user to update or delete a specific data entry.
 * The activity retrieves the data from the Firebase Realtime Database based on the provided key,
 * populates the EditText fields with the retrieved data, and provides options to update or delete the data.
 */
public class UpdateDeleteActivity extends AppCompatActivity {

    private EditText commentEditText, diaPressureEditText, heartRateEditText, sysPressureEditText;
    private Button updateButton, deleteButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private String key;
    /**
     * Called when the activity is starting. This is where most initialization of the activity should go.
     * Sets the content view to display the update_delete layout.
     * Retrieves the key from the intent extras and fetches the corresponding data from the database.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     * Otherwise, it is null.
     */
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
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            key = extras.getString("key");
        }

        databaseReference.child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String comment = dataSnapshot.child("Comment").getValue(String.class);
                    String diaPressure = dataSnapshot.child("DiaPressure").getValue(String.class);
                    String heartRate = dataSnapshot.child("HeartRate").getValue(String.class);
                    String sysPressure = dataSnapshot.child("SysPressure").getValue(String.class);

                    // Populate the EditText fields with the retrieved data
                    commentEditText.setText(comment != null ? comment : "");
                    diaPressureEditText.setText(diaPressure != null ? diaPressure : "");
                    heartRateEditText.setText(heartRate != null ? heartRate : "");
                    sysPressureEditText.setText(sysPressure != null ? sysPressure : "");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle the error if retrieval is unsuccessful
                Toast.makeText(UpdateDeleteActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    /**
     * Updates the data fields in the database with the values entered in the EditText fields.
     * Displays a toast message upon successful update.
     * If any of the fields are empty, displays a toast message to fill in all the fields.
     */
    private void updateData() {
        String comment = commentEditText.getText().toString().trim();
        String diaPressure = diaPressureEditText.getText().toString().trim();
        String heartRate = heartRateEditText.getText().toString().trim();
        String sysPressure = sysPressureEditText.getText().toString().trim();

        if (comment.isEmpty() || diaPressure.isEmpty() || heartRate.isEmpty() || sysPressure.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update the data fields in the database using the key
        databaseReference.child(key).child("Comment").setValue(comment);
        databaseReference.child(key).child("DiaPressure").setValue(diaPressure);
        databaseReference.child(key).child("HeartRate").setValue(heartRate);
        databaseReference.child(key).child("SysPressure").setValue(sysPressure);

        Toast.makeText(this, "Data updated successfully!", Toast.LENGTH_SHORT).show();
    }
    /**
     * Displays a confirmation dialog to confirm the deletion of the data.
     * If the user confirms, calls the deleteData() method to delete the data.
     */
    private void showConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("Confirmation")
                .setMessage("Are you sure you want to delete this data?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    deleteData();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    /**
     * Deletes the data from the database using the key.
     * Displays a toast message upon successful deletion.
     * After deletion, finishes the activity and returns to the previous activity.
     */
    private void deleteData() {
        // Remove the data from the database using the key
        databaseReference.child(key).removeValue();

        Toast.makeText(this, "Data deleted successfully!", Toast.LENGTH_SHORT).show();
        finish(); // Return to the previous activity
    }
}
