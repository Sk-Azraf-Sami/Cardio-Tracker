package com.example.cardiotracker;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cardiotracker.SignUp;
import com.example.cardiotracker.R;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignUpUITest {

    @Rule
    public ActivityScenarioRule<SignUp> activityScenarioRule = new ActivityScenarioRule<>(SignUp.class);

    @Test
    public void signUpSuccessTest() {
        String email = "test2@example.com";
        String password = "password123";

        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.signup))
                .perform(ViewActions.click());

        // Add assertions based on the actual behavior of your app after successful sign-up
        // For example, you can check if a toast message is displayed:
        Espresso.onView(ViewMatchers.withText("Account Created"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(ViewMatchers.withId(android.R.id.content))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void signUpEmptyFieldsTest() {
        Espresso.onView(ViewMatchers.withId(R.id.signup))
                .perform(ViewActions.click());

        // Add assertions based on the actual behavior of your app when sign-up fields are empty
        // For example, you can check if a toast message is displayed:
        Espresso.onView(ViewMatchers.withText("Enter Email"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(ViewMatchers.withId(android.R.id.content))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void signUpAuthenticationFailedTest() {
        String email = "invalid@example.com";
        String password = "password123";

        Espresso.onView(ViewMatchers.withId(R.id.email))
                .perform(ViewActions.typeText(email), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.password))
                .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard());

        Espresso.onView(ViewMatchers.withId(R.id.signup))
                .perform(ViewActions.click());

        // Add assertions based on the actual behavior of your app when sign-up authentication fails
        // For example, you can check if a toast message is displayed:
        Espresso.onView(ViewMatchers.withText("Authentication failed"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(ViewMatchers.withId(android.R.id.content))))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
}
