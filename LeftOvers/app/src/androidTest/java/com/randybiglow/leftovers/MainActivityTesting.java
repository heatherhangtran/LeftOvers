package com.randybiglow.leftovers;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTesting {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    //Testing the fab button is clickable.
    @Test
    public void fabButtonClicked(){
        onView(withId(R.id.fab)).check(matches(isClickable()));
    }

    //Testing the appearance of the text within the alert dialog.
//    @Test
//    public void textInDialog(){
//        //onView(withText("Add New Item?")).check(matches(isDisplayed()));
//        onView(withText(R.string.dialog_addnew)).inRoot(isDialog()).check(matches(isDisplayed()));
//    }


//    @Test
//    public void nameEditText(){
//        //onView(withId(R.id.name)).check(matches(isEnabled()));
//    }

    //Testing the fragments.
//    @Test
//    public void showTextInRecipeFragment(){
//        ViewInteraction fragmentText = onView(withId(R.id.recipes));
//        fragmentText.check(ViewAssertions.doesNotExist());
//        //fragmentText.check(ViewAssertions.matches(isDisplayed)));
//    }


}