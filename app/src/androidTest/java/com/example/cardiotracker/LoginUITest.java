package com.example.cardiotracker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

//import com.example.cardiotracker.ui.MainActivity;
//import com.example.cardiotracker.ui.auth.SignUp;
import com.example.cardiotracker.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginUITest {

    @Rule
    public IntentsTestRule<Login> activityRule = new IntentsTestRule<>(Login.class);

    private CountingIdlingResource countingIdlingResource;

    @Before
    public void setUp() {
        countingIdlingResource = new CountingIdlingResource("FirebaseAuthentication");
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }

    @After
    public void tearDown() {
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }

    @Test
    public void loginSuccess() {

        String test_email = "check@gmail.com";
        String test_password = "kacchi";
        // Perform login with valid credentials
        Espresso.onView(withId(R.id.emailEditText)).perform(ViewActions.typeText(test_email));
        Espresso.onView(withId(R.id.passwordEditText)).perform(ViewActions.typeText(test_password));

        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        // Increment the CountingIdlingResource before starting the Firebase authentication task
        countingIdlingResource.increment();

        // Wait for Firebase authentication to complete
        FirebaseAuth.getInstance().signInWithEmailAndPassword(test_email, test_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // Decrement the CountingIdlingResource after the Firebase authentication task is completed
                        countingIdlingResource.decrement();
                    }
                });

        // Verify that MainActivity is launched
        intended(hasComponent(MainActivity.class.getName()));
    }

    @Test
    public void loginFailure() {

        String test_email = "invalid@gmail.com";
        String test_password = "wrongpassword";
        // Perform login with invalid credentials
        Espresso.onView(withId(R.id.emailEditText)).perform(ViewActions.typeText(test_email));
        Espresso.onView(withId(R.id.passwordEditText)).perform(ViewActions.typeText(test_password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.loginButton)).perform(ViewActions.click());

        // Wait for Firebase authentication to complete
        countingIdlingResource.increment();

        // Verify that the login failed (Stay on the Login activity)
        FirebaseAuth.getInstance().signInWithEmailAndPassword(test_email, test_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // Decrement the CountingIdlingResource after the Firebase authentication task is completed
                        countingIdlingResource.decrement();
                        if (!task.isSuccessful()) {
                            // Authentication failed
                            Espresso.onView(withText("Authentication failed"))
                                    .check(matches(isDisplayed()));
                        }
                    }
                });
    }



    @Test
    public void signUpLinkClick() {
        // Perform click on the "Create Account" link
        Espresso.onView(withId(R.id.createAccountTextView)).perform(ViewActions.click());

        // Verify that SignUp activity is launched
        intended(hasComponent(SignUp.class.getName()));
    }
}
