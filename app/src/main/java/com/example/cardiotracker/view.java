package com.example.cardiotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view extends AppCompatActivity {

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
                List<String> dataList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Assuming the data is stored under a child with a unique ID
                    dataSnapshotList.add(snapshot);

                    // Access the data fields
                    String comment = snapshot.child("Comment").getValue(String.class);
                    String heartRate = snapshot.child("HeartRate").getValue(String.class);
                    String sysPressure = snapshot.child("SysPressure").getValue(String.class);
                    String diaPressure = snapshot.child("DiaPressure").getValue(String.class);
                    String systemDate = snapshot.child("SystemDate").getValue(String.class);
                    String systemTime = snapshot.child("SystemTime").getValue(String.class);

                    // Create a string representation of the data
                    String data = "Comment: " + comment +
                            "\nDiaPressure: " + diaPressure +
                            "\nHeartRate: " + heartRate +
                            "\nSysPressure: " + sysPressure +
                            "\nSystemDate: " + systemDate +
                            "\nSystemTime: " + systemTime;

                    dataList.add(data);
                }

                // Update the UI with the fetched data
                ListView listView = findViewById(R.id.listView);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(view.this,
                        android.R.layout.simple_list_item_1, dataList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("view", "Error fetching data", databaseError.toException());
            }
        };

        // Attach the value event listener to the database reference
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void openUpdateDeleteActivity(String key) {
        Intent intent = new Intent(this, UpdateDeleteActivity.class);
        intent.putExtra("key", key);
        startActivity(intent);
    }
}
