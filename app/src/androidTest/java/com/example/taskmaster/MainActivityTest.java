package com.example.taskmaster;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.EnumSet.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public static final String username = "Mohammad";
    public static final String taskName = "taskName";
    public static final String taskBody = "taskBody";
    public static final String taskState = "taskState";

    @Test
    public void testSettingButton() {
        onView(withId(R.id.settings)).perform(click());
        onView(withId(R.id.username)).perform(typeText(username));
        onView(withId(R.id.save)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.textView1)).check(matches(withText( username + "’s tasks")));
    }

    @Test
    public void testAddTaskButton() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.editTextTextPersonName)).perform(typeText(taskName));
        onView(withId(R.id.editTextTextPersonName2)).perform(typeText(taskBody));
        onView(withId(R.id.editTextTextPersonName3)).perform(typeText(taskState));
        closeSoftKeyboard();
        onView(withId(R.id.button3)).perform(click());
    }

}
