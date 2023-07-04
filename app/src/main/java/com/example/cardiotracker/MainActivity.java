package com.example.cardiotracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Remove the title bar (action bar)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();

        // Make the activity full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ImageView heartIcon = findViewById(R.id.hearticon);
        heartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the list_item_view activity
                Intent intent = new Intent(MainActivity.this, list_item_view.class);
                startActivity(intent);
            }
        });

        ImageView newRecord = findViewById(R.id.newrecord);
        newRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the list_item_view activity
                Intent intent = new Intent(MainActivity.this, AddRecord.class);
                startActivity(intent);
            }
        });

        ImageView aboutUs = findViewById(R.id.aboutus);
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the list_item_view activity
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
            }
        });

        ImageView privacyPolicy = findViewById(R.id.privacypolicy);
        privacyPolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the list_item_view activity
                Intent intent = new Intent(MainActivity.this, PrivacyPolicy.class);
                startActivity(intent);
            }
        });

        ImageView githubIcon = findViewById(R.id.github);
        githubIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the GitHub repository link
                String githubRepoUrl = "https://github.com/Sk-Azraf-Sami/Cardio-Tracker";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubRepoUrl));
                startActivity(intent);
            }
        });

        ImageView logoutIcon = findViewById(R.id.logout);
        logoutIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform logout and navigate back to the login page
                logout();
                navigateToLogin();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void logout() {
        finish();
    }

    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish(); // Close the MainActivity
    }
}
