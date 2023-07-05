package com.example.cardiotracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
/**
 * This class represents the main activity of the application.
 * It provides the main user interface and navigation options.
 * The activity includes options to view existing health records, add a new health record,
 * view information about the application, view the privacy policy, open the GitHub repository,
 * and log out from the application.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. This is where most initialization of the activity should go.
     * Sets the activity to full screen by removing the title bar and making the activity full screen.
     * Initializes the UI elements and sets click listeners for the various options in the activity.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
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
    /**
     * Shows a confirmation dialog for logout when the logout icon is clicked.
     * If the user confirms the logout, it performs the logout action and navigates back to the login page.
     */
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
    /**
     * Performs the logout action.
     * This method is called when the user confirms the logout.
     * It finishes the current activity, effectively logging out the user.
     */
    private void logout() {
        finish();
    }

    /**
     * Navigates back to the login page.
     * This method is called after performing the logout action.
     * It starts the Login activity and finishes the current activity.
     */
    private void navigateToLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish(); // Close the MainActivity
    }
}
