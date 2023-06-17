package com.example.cardiotracker;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.cardiotracker.AddRecord;
import com.example.cardiotracker.R;

public class AddRecordUITest {

    @Rule
    public ActivityScenarioRule<AddRecord> activityScenarioRule = new ActivityScenarioRule<>(AddRecord.class);

    @Test
    public void testClearButtonClearsFields() {
        // Start the activity scenario
        ActivityScenario<AddRecord> scenario = activityScenarioRule.getScenario();

        // Type text into the EditText fields
        Espresso.onView(ViewMatchers.withId(R.id.editHeartRate)).perform(ViewActions.typeText("70"));
        Espresso.onView(ViewMatchers.withId(R.id.editSystolicPressure)).perform(ViewActions.typeText("120"));
        Espresso.onView(ViewMatchers.withId(R.id.editDiastolicPressure)).perform(ViewActions.typeText("80"));
        Espresso.onView(ViewMatchers.withId(R.id.editAddComment)).perform(ViewActions.typeText("Sample comment"));
        Espresso.pressBack(); //Back button // ----------------for this I get test failed error

        // Verify that the text is entered correctly
        Espresso.onView(ViewMatchers.withId(R.id.editHeartRate)).check(matches(withText("70")));
        Espresso.onView(ViewMatchers.withId(R.id.editSystolicPressure)).check(matches(withText("120")));
        Espresso.onView(ViewMatchers.withId(R.id.editDiastolicPressure)).check(matches(withText("80")));
        Espresso.onView(ViewMatchers.withId(R.id.editAddComment)).check(matches(withText("Sample comment")));

        // Click the clear button
        Espresso.onView(ViewMatchers.withId(R.id.btnClear)).perform(ViewActions.click());

        // Verify that the EditText fields are cleared
        Espresso.onView(ViewMatchers.withId(R.id.editHeartRate)).check(matches(withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.editSystolicPressure)).check(matches(withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.editDiastolicPressure)).check(matches(withText("")));
        Espresso.onView(ViewMatchers.withId(R.id.editAddComment)).check(matches(withText("")));


        // Close the activity scenario
        scenario.close();
    }
}
