package com.example.cardiotracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class SignUp extends AppCompatActivity {

    private TextInputEditText emailEditText, passwordEditText;
    private Button signUpButton;
    private TextView alreadyAccountTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signupButton);
        alreadyAccountTextView = findViewById(R.id.AlreadyAccountTextView);

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

    private boolean validateFields(String email, String password) {
        // Implement your own validation logic here
        // You can check if the email is valid using a regular expression or other methods
        // You can also check if the password meets your criteria for strength

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
