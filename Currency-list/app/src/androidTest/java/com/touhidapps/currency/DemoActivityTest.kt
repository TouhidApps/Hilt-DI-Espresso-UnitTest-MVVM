package com.touhidapps.currency

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withText
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.matcher.ViewMatchers.Visibility
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.google.android.material.datepicker.CompositeDateValidator.allOf
import com.touhidapps.currency.adapter.CurrencyAdapter
import org.hamcrest.Matcher
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.runner.Description

@RunWith(AndroidJUnit4::class)
class DemoActivityTest {

    // Declear activity
    @get:Rule
    val activityRule = ActivityScenarioRule(DemoActivity::class.java)

    @Test
    fun test_InsertDataButton_AllDataAvailable() = runTest {
        onView(withId(R.id.btnInsert)).perform(click()) // Insert all data
        onView(withId(R.id.btnShowAll)).perform(click()) // Go to data view page

        onView(withId(R.id.llNoDataMessage)).isGone() // As there are some data so this UI will remain gone
    }

    @Test
    fun test_ClearButtonClick_NoDataInUI() = runTest {
        onView(withId(R.id.btnInsert)).perform(click()) // Insert all data

        // Click the button that shows the alert dialog
        onView(withId(R.id.btnClearAll)).perform(click())

        // Click the "OK" button in the alert dialog
        onView(withText("OK")).perform(click()) // Clear all data

        onView(withId(R.id.btnShowAll)).perform(click())

        onView(withId(R.id.llNoDataMessage)).isVisible() // As there are no data so this UI will be visible
    }

    @Test
    fun test_CryptoList_IsItCryptoList() = runTest {

        val recyclerViewId = R.id.recyclerView
        val itemPosition = 0
        val expectedText = "Bitcoin"
        val unExpectedText = "Singapore Dollar"

        onView(withId(R.id.btnInsert)).perform(click()) // Insert all data

        onView(withId(R.id.btnCrypto)).perform(click())

        // Ensure the item is visible
        onView(withId(recyclerViewId)).perform(scrollToPosition<CurrencyAdapter.MyViewHolder>(itemPosition))

        onView(withText(expectedText)).check(matches(isDisplayed()))

        // Check fiat available or not
        onView(withText(unExpectedText)).check(ViewAssertions.doesNotExist())
        onView(withId(recyclerViewId)).perform(scrollToLastPosition<CurrencyAdapter.MyViewHolder>())
        onView(withText(unExpectedText)).check(ViewAssertions.doesNotExist())

    }

    @Test
    fun test_FiatList_IsItFiatList() = runTest {

        val recyclerViewId = R.id.recyclerView
        val itemPosition = 0
        val expectedText = "Singapore Dollar"
        val unExpectedText = "Bitcoin"

        onView(withId(R.id.btnInsert)).perform(click()) // Insert all data

        onView(withId(R.id.btnFiat)).perform(click())

        // Ensure the item is visible
        onView(withId(recyclerViewId)).perform(scrollToPosition<CurrencyAdapter.MyViewHolder>(itemPosition))

        onView(withText(expectedText)).check(matches(isDisplayed()))

        // Check fiat available or not
        onView(withText(unExpectedText)).check(ViewAssertions.doesNotExist())
        onView(withId(recyclerViewId)).perform(scrollToLastPosition<CurrencyAdapter.MyViewHolder>())
        onView(withText(unExpectedText)).check(ViewAssertions.doesNotExist())

    }

}