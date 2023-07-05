package com.example.cardiotracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
/**
 * This activity allows users to sign up for an account by providing their email and password.
 * Upon successful signup, the user is navigated to the login screen. If signup fails, appropriate error messages are displayed.
 */
public class SignUp extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button signUpButton;
    private TextView alreadyAccountTextView;
    private FirebaseAuth mAuth;
    private CheckBox showPasswordCheckBox;
    /**
     * Called when the activity is starting. This is where most initialization of the activity should go.
     * Sets the content view to display the signup layout.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signupButton);
        alreadyAccountTextView = findViewById(R.id.AlreadyAccountTextView);
        showPasswordCheckBox = findViewById(R.id.showPasswordCheckBox);

        showPasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show password
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // Hide password
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                // Validate email and password fields
                if (validateFields(email, password)) {
                    // Create new user with email and password
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Signup success, show toast notification
                                        Toast.makeText(SignUp.this, "Signup successful", Toast.LENGTH_SHORT).show();

                                        // Navigate to the Login activity
                                        Intent intent = new Intent(SignUp.this, Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        // Signup failed, display error message
                                        Exception exception = task.getException();
                                        if (exception instanceof FirebaseAuthWeakPasswordException) {
                                            Toast.makeText(SignUp.this, "Weak password. Please choose a stronger password.", Toast.LENGTH_SHORT).show();
                                        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                                            Toast.makeText(SignUp.this, "Invalid email. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                                        } else if (exception instanceof FirebaseAuthUserCollisionException) {
                                            Toast.makeText(SignUp.this, "An account with this email already exists.", Toast.LENGTH_SHORT).show();
                                        } else if (exception instanceof FirebaseNetworkException) {
                                            Toast.makeText(SignUp.this, "Network error. Please check your internet connection and try again.", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(SignUp.this, "Signup failed. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            });
                }
            }
        });

        alreadyAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to the Login activity
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
    /**
     * Validates the email and password fields.
     * Displays appropriate toast messages for invalid fields.
     *
     * @param email    The email entered by the user
     * @param password The password entered by the user
     * @return True if the fields are valid, False otherwise
     */
    private boolean validateFields(String email, String password) {

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter an email address.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
