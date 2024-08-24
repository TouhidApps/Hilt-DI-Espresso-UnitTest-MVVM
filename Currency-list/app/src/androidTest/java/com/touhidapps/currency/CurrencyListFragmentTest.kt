package com.touhidapps.currency

import android.view.View
import android.widget.SearchView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToLastPosition
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.touhidapps.currency.adapter.CurrencyAdapter
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CurrencyListFragmentTest {

    // Declear activity
    @get:Rule
    val activityRule = ActivityScenarioRule(DemoActivity::class.java)

    @Test
    fun test_search_result() = runTest {

        val searchText = "Ethereum"
        val expectedText1 = "Ethereum"
        val expectedText2 = "Ethereum Classic"
        val unExpectedText = "Bitcoin"

        onView(withId(R.id.btnInsert)).perform(click()) // Insert all data

        onView(withId(R.id.btnShowAll)).perform(click())

        onView(withId(R.id.searchView)).perform(click())
        onView(withId(R.id.searchView)).perform(typeText(searchText))

        onView(ViewMatchers.withId(R.id.recyclerView)).check(ViewAssertions.matches(
            ViewMatchers.hasDescendant(ViewMatchers.withText(expectedText1)) // same text available is in search box so find parent using id first
        ))
        onView(withText(expectedText2)).check(matches(isDisplayed()))
        onView(withText(unExpectedText)).check(ViewAssertions.doesNotExist())

    }





}