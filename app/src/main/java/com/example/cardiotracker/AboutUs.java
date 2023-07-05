package com.example.cardiotracker;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


/**
 * The AboutUs class represents an activity that displays information about the application or its developers.
 * This activity removes the title bar (action bar) and makes the activity full screen.
 */
public class AboutUs extends AppCompatActivity {

    /**
     * Called when the activity is starting. This method sets up the activity layout and appearance.
     *
     * @param savedInstanceState a Bundle containing the saved state of the activity, or null if there is no saved state
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

        setContentView(R.layout.activity_about_us);
    }
}