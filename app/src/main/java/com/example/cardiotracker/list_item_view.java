package com.example.cardiotracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
/**
 * This class represents the activity that displays a list of health data records for the current user.
 * It fetches data from the Firebase Realtime Database and populates the list view with health data records.
 * The user can click on a list item to open the UpdateDeleteActivity for modifying or deleting the selected record.
 */
public class list_item_view extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private List<DataSnapshot> dataSnapshotList;


    /**
     * Called when the activity is starting. This is where most initialization of the activity should go.
     * Sets up the Firebase Authentication instance and retrieves the reference to the current user's data in the database.
     * Calls the fetchDataFromDatabase method to populate the list view with health data records.
     * Sets an item click listener on the list view to open the UpdateDeleteActivity when a list item is clicked.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        // Get the reference for the current user's data in the database
        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser.getUid());

        fetchDataFromDatabase();

        ListView listView = findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DataSnapshot dataSnapshot = dataSnapshotList.get(position);
                String key = dataSnapshot.getKey();
                openUpdateDeleteActivity(key);
            }
        });
    }
    /**
     * Fetches health data records from the Firebase Realtime Database for the current user.
     * Uses a ValueEventListener to listen for changes in the data and populate the list view accordingly.
     * If the data retrieval fails, an error message is logged.
     */
    private void fetchDataFromDatabase() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshotList = new ArrayList<>();
                List<HealthData> dataList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    dataSnapshotList.add(snapshot);

                    // Retrieve data from snapshot
                    String comment = snapshot.child("Comment").getValue(String.class);
                    String heartRateString = snapshot.child("HeartRate").getValue(String.class);
                    String sysPressure = snapshot.child("SysPressure").getValue(String.class);
                    String diaPressure = snapshot.child("DiaPressure").getValue(String.class);
                    String systemDate = snapshot.child("SystemDate").getValue(String.class);
                    String systemTime = snapshot.child("SystemTime").getValue(String.class);

                    ImageView statusIndicator = new ImageView(list_item_view.this);
                    int heartRate = 0;
                    int sysPressureInt = 0;
                    int diaPressureInt = 0;

                    // Convert the heartRateString to an integer
                    try {
                        if (heartRateString != null) {
                            String numericValue = heartRateString.split(" ")[0];
                            heartRate = Integer.parseInt(numericValue);
                        }
                    } catch (NumberFormatException e) {
                        Log.e("HealthDataAdapter", "Invalid heart rate format", e);
                    }

                    // Convert the sysPressure to an integer
                    try {
                        if (sysPressure != null) {
                            String numericValue = sysPressure.split(" ")[0];
                            sysPressureInt = Integer.parseInt(numericValue);
                        }
                    } catch (NumberFormatException e) {
                        Log.e("HealthDataAdapter", "Invalid sys pressure format", e);
                    }

                    // Convert the diaPressure to an integer
                    try {
                        if (diaPressure != null) {
                            String numericValue = diaPressure.split(" ")[0];
                            diaPressureInt = Integer.parseInt(numericValue);
                        }
                    } catch (NumberFormatException e) {
                        Log.e("HealthDataAdapter", "Invalid dia pressure format", e);
                    }

                    if (heartRate >= 60 && heartRate <= 100 && diaPressureInt >= 60 && diaPressureInt <= 80 && sysPressureInt >= 90 && sysPressureInt <= 120) {
                        statusIndicator.setImageResource(R.drawable.green_circle); // Normal status
                    } else {
                        statusIndicator.setImageResource(R.drawable.red_circle); // Abnormal status
                    }

                    String heartRateWithUnit = heartRateString + " bpm";
                    String diaPressureWithUnit = diaPressure + " mmHg";
                    String sysPressureWithUnit = sysPressure + " mmHg";

                    HealthData healthData = new HealthData(systemDate, systemTime, diaPressureWithUnit, sysPressureWithUnit, heartRateWithUnit, comment);
                    dataList.add(healthData);
                }

                ListView listView = findViewById(R.id.listView);
                HealthDataAdapter adapter = new HealthDataAdapter(list_item_view.this, dataList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("view", "Error fetching data", databaseError.toException());
            }
        };

        databaseReference.addValueEventListener(valueEventListener);
    }
    /**
     * Opens the UpdateDeleteActivity to modify or delete the selected health data record.
     *
     * @param key The key of the selected health data record in the Firebase Realtime Database.
     *            This key is passed as an extra in the Intent to the UpdateDeleteActivity.
     */
    private void openUpdateDeleteActivity(String key) {
        Intent intent = new Intent(this, UpdateDeleteActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}
