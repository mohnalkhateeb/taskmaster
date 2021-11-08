package com.example.taskmaster;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);
    public static final String username = "Mohammad";
    public static final String taskName = "taskName";
    public static final String taskBody = "taskBody";
    public static final String taskState = "NEW";

    @Test
    public void testSettingButton() {
        onView(withId(R.id.settings)).perform(click());
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.save)).perform(click());
        onView(withId(R.id.textView1)).check(matches(withText(username + "'s Task List")));
        closeSoftKeyboard();
    }
    @Test
    public void testAddTaskButton() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.editTextTextPersonName)).perform(typeText(taskName));
        onView(withId(R.id.editTextTextPersonName2)).perform(typeText(taskBody));
        onView(withId(R.id.editTextTextPersonName3)).perform(typeText(taskState));
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.textView1)).check(matches(withText(username + "'s Task List")));
        closeSoftKeyboard();
    }
    @Test public void testingRecyclerView(){
        onView(withId(R.id.recyclerView23)).perform(click());
        onView(withId(R.id.textView7)).check(matches(withText(taskName)));
        onView(withId(R.id.textView8)).check(matches(withText(taskBody+" "+taskState)));

    }

}
