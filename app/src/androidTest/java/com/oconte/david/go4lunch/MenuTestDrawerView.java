package com.oconte.david.go4lunch;

import android.content.SharedPreferences;
import android.view.Gravity;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.oconte.david.go4lunch.auth.AuthActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.DrawerMatchers.isClosed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.oconte.david.go4lunch.auth.AuthActivity.EXTRA_IS_CONNECTED;

@RunWith(AndroidJUnit4.class)
public class MenuTestDrawerView {



    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<MainActivity>(MainActivity.class, false, false);

    @Test
    public void testClickActionBarItemMenuDrawer() throws IOException, InterruptedException {

        // a mettre en variable .InstrumentationRegistry.getInstrumentation().getTargetContext();

        //j'ouvre ma sharedpreference et je lui met la valeur true.

        SharedPreferences preferences = InstrumentationRegistry.getInstrumentation().getTargetContext().getSharedPreferences("EXTRA_LOG", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(EXTRA_IS_CONNECTED, true);
        editor.apply();
        

        //Start the MainActivity
        mActivityRule.launchActivity(null);
        //resultLog =


        //Select the toolbar
        onView(withId(R.id.toolbar)).perform(click());

        // Select the drawer menu
        onView(withId(R.id.activity_main_nav_view))
                .check(matches(isClosed(Gravity.LEFT)))
                .perform(DrawerActions.open());

        //Select the first item
        //onView(withId(R.id.activity_main_drawer_lunch)).perform(click());



    }

    @Test
    public void testClickActionBar() throws IOException, InterruptedException {

        //Start the MainActivity
        mActivityRule.launchActivity(null);

        //Select the toolbar
        onView(withId(R.id.toolbar)).perform(click());




    }

}
