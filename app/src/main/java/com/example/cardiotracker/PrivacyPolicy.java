package com.example.cardiotracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/**
 * This class represents the activity that displays the privacy policy of the application.
 * It provides information about the privacy practices and policies followed by the application.
 * The activity shows the privacy policy content to the user.
 */
public class PrivacyPolicy extends AppCompatActivity {
    /**
     * Called when the activity is starting. This is where most initialization of the activity should go.
     * Sets the content view to display the privacy policy layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
    }
}