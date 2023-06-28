package com.example.cardiotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    private Button btnLogout;
    private Button btnAddRecord;
    private Button btnView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogout = findViewById(R.id.btnLogout); // Replace with your logout button ID

        // Set click listener for the logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to navigate to the LoginActivity
                Intent intent = new Intent(MainActivity.this, Login.class);
                // Clear the back stack, so the user cannot navigate back to the current activity
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                // Start the LoginActivity and finish the current activity
                startActivity(intent);
                finish();
            }
        });

        btnAddRecord = findViewById(R.id.btnAddNewRecord);
        btnAddRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecord.class);
                startActivity(intent);
            }
        });

        btnView=findViewById(R.id.btnAllInformation);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, list_item_view.class);
                startActivity(intent);

            }
        });


    }
}