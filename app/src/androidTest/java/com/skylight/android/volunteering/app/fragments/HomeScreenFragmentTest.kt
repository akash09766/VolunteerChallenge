package com.skylight.android.volunteering.app.fragments

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.skylight.android.volunteering.R
import com.skylight.android.volunteering.app.activities.MainActivity
import com.skylight.android.volunteering.app.adapter.VehicleListAdapter
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Akash Wangalwar.(Github:akash09766) on 24-02-2022 at 20:28.
 */

@RunWith(AndroidJUnit4::class)
class HomeScreenFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun test_loading_state_when_fragment_launch() {
        activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.vehicle_list)).check(matches(not(isDisplayed())))
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())))
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun test_data_load_state_when_fragment_launch() {
        onView(withId(R.id.vehicle_list)).perform(waitUntil(isDisplayed()))
        with(onView(withId(R.id.vehicle_list)).perform(waitUntil(isDisplayed()))) {
            check(matches(EspressoTestsHelpers.recyclerViewSizeMatcher(30)))
        }
        onView(withId(R.id.error_tv)).check(matches(not(isDisplayed())))
        onView(withId(R.id.progressBar)).check(matches(not(isDisplayed())))
    }

    @Test
    fun scroll_to_eleventh_item_and_click_UI_test() {
        activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.vehicle_list)).perform(waitUntil(isDisplayed()))
        onView(withId(R.id.vehicle_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<VehicleListAdapter.ItemViewHolder>(
                10,
                click()
            )
        )
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

    @Test
    fun test_navigation_to_map_fragment() {
        activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.vehicle_list)).perform(waitUntil(isDisplayed()))
        onView(withId(R.id.vehicle_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<VehicleListAdapter.ItemViewHolder>(
                10,
                click()
            )
        )
        onView(withId(R.id.map)).check(matches(isDisplayed()))
        onView(withContentDescription("Google Map")).perform(click());
    }

    @Test
    fun test_back_navigation_on_map_fragment() {
        activityRule.scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.vehicle_list)).perform(waitUntil(isDisplayed()))
        onView(withId(R.id.vehicle_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<VehicleListAdapter.ItemViewHolder>(
                10,
                click()
            )
        )
        onView(withId(R.id.map)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.vehicle_list)).perform(waitUntil(isDisplayed()))
    }
}