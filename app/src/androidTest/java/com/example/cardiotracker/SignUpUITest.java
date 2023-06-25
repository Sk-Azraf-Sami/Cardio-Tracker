package com.example.cardiotracker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cardiotracker.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SignUpUITest {

    @Rule
    public IntentsTestRule<SignUp> activityRule = new IntentsTestRule<>(SignUp.class);

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
    public void signUpSuccess() {

        // -------------------------------------------- for every test change this mail
        String test_email = "check78966@gmail.com";
        String test_password = "kacchi";

        // Perform sign up with valid email and password
        onView(withId(R.id.emailEditText)).perform(ViewActions.typeText(test_email));
        onView(withId(R.id.passwordEditText)).perform(ViewActions.typeText(test_password));

        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupButton)).perform(ViewActions.click());

        // Increment the CountingIdlingResource before starting the Firebase authentication task
        countingIdlingResource.increment();

        // Perform the Firebase authentication task
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(test_email, test_password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // Decrement the CountingIdlingResource after the Firebase authentication task is completed
                        countingIdlingResource.decrement();
                    }
                });

        // Wait for the authentication process to complete
        IdlingResource idlingResource = countingIdlingResource;
        IdlingRegistry.getInstance().register(idlingResource);

        // Wait until the authentication task is completed
        IdlingRegistry.getInstance().register(new IdlingResource() {
            private ResourceCallback resourceCallback;

            @Override
            public String getName() {
                return "FirebaseAuthentication";
            }

            @Override
            public boolean isIdleNow() {
                boolean idle = countingIdlingResource.isIdleNow();
                if (idle && resourceCallback != null) {
                    resourceCallback.onTransitionToIdle();
                }
                return idle;
            }

            @Override
            public void registerIdleTransitionCallback(ResourceCallback callback) {
                resourceCallback = callback;
            }
        });

        // Verify that Login activity is launched
        intended(hasComponent(Login.class.getName()));

        // Unregister the idling resources
        IdlingRegistry.getInstance().unregister(idlingResource);
        IdlingRegistry.getInstance().unregister(countingIdlingResource);
    }

    @Test
    public void signUpLinkClick() {
        // Perform click on the "Create Account" link
        Espresso.onView(withId(R.id.AlreadyAccountTextView)).perform(ViewActions.click());

        // Verify that SignUp activity is launched
        intended(hasComponent(Login.class.getName()));
    }

}
