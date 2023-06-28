package com.example.cardiotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class list_item_view extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private List<DataSnapshot> dataSnapshotList;

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
                    String systemDateString = snapshot.child("SystemDate").getValue(String.class);
                    String systemTimeString = snapshot.child("SystemTime").getValue(String.class);

                    // Parse system date
                    Date systemDate = null;
                    try {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                        systemDate = dateFormat.parse(systemDateString);
                    } catch (ParseException e) {
                        Log.e("view", "Invalid system date format", e);
                    }

                    long systemDateLong = 0;
                    if (systemDate != null) {
                        systemDateLong = systemDate.getTime();
                    }

                    // Parse system time
                    Date time = null;
                    try {
                        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss a", Locale.getDefault());
                        time = timeFormat.parse(systemTimeString);
                    } catch (ParseException e) {
                        Log.e("view", "Invalid system time format", e);
                    }

                    long systemTime = 0;
                    if (time != null) {
                        systemTime = time.getTime();
                    }

                    // Format the date
                    Date date = new Date(systemDateLong);
                    DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = dateFormat.format(date);

                    // Format the time
                    DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
                    String formattedTime = timeFormat.format(time);

                    // Convert the heartRateString to an integer
                    int heartRate = 0;
                    try {
                        String numericValue = heartRateString.split(" ")[0];
                        heartRate = Integer.parseInt(numericValue);
                    } catch (NumberFormatException e) {
                        Log.e("HealthDataAdapter", "Invalid heart rate format", e);
                    }

                    // Convert the sysPressure to an integer
                    int sysPressureInt = 0;
                    try {
                        String numericValue = sysPressure.split(" ")[0];
                        sysPressureInt = Integer.parseInt(numericValue);
                    } catch (NumberFormatException e) {
                        Log.e("HealthDataAdapter", "Invalid sys pressure format", e);
                    }

                    // Convert the diaPressure to an integer
                    int diaPressureInt = 0;
                    try {
                        String numericValue = diaPressure.split(" ")[0];
                        diaPressureInt = Integer.parseInt(numericValue);
                    } catch (NumberFormatException e) {
                        Log.e("HealthDataAdapter", "Invalid dia pressure format", e);
                    }

                    ImageView statusIndicator = new ImageView(list_item_view.this);
                    if (heartRate >= 60 && heartRate <= 100 && diaPressureInt >= 60 && diaPressureInt <= 80 && sysPressureInt >= 90 && sysPressureInt <= 120) {
                        statusIndicator.setImageResource(R.drawable.green_circle); // Normal status
                    } else {
                        statusIndicator.setImageResource(R.drawable.red_circle); // Abnormal status
                    }

                    String heartRateWithUnit = heartRateString + " bpm";
                    String diaPressureWithUnit = diaPressure + " mmHg";
                    String sysPressureWithUnit = sysPressure + " mmHg";

                    HealthData healthData = new HealthData(formattedDate, formattedTime, diaPressureWithUnit, sysPressureWithUnit, heartRateWithUnit, comment);
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

    private void openUpdateDeleteActivity(String key) {
        Intent intent = new Intent(this, UpdateDeleteActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}
