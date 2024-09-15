package com.touhidapps.currency

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.hamcrest.Matchers.*
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import java.util.regex.Pattern.matches

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@ExperimentalMaterial3Api
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@HiltAndroidTest
class DemoActivityUITest {

    @get:Rule(order = 1)
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    var composeTestRule = createAndroidComposeRule<DemoActivity>()

    @Before
    fun setup() {
        hiltTestRule.inject()
    } // setup


    @Test
    fun test1_availableAllButtons() = runTest {

        val btnTags = listOf<String>(
            "btnClearAll",
            "btnInsert",
            "btnCrypto",
            "btnFiat",
            "btnShowAll"
        )

        btnTags.forEach {
            composeTestRule.onNodeWithTag(it).assertIsDisplayed()
        }

    }

    @Test
    fun test2_clearAllDataWithAlert() = runTest {

        composeTestRule.onNodeWithTag("btnInsert").performClick()
        composeTestRule.onNodeWithTag("btnClearAll").performClick()
        composeTestRule.onNodeWithText("Yes").performClick()
        composeTestRule.onNodeWithTag("btnShowAll").performClick()

        // As there are no data so this UI will be visible
        composeTestRule.onNodeWithTag("noDataMessage").assertIsDisplayed()

    }

    @Test
    fun test3_CryptoList_IsItCryptoList() = runTest {

        val expectedText = "Bitcoin"
        val unExpectedText = "Singapore Dollar"

        composeTestRule.onNodeWithTag("btnInsert").performClick()
        composeTestRule.onNodeWithTag("btnCrypto").performClick()

        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
        composeTestRule.onNodeWithText(unExpectedText).assertIsNotDisplayed()

    }

    @Test
    fun test4_FiatList_IsItFiatList() = runTest {

        val expectedText = "Singapore Dollar"
        val unExpectedText = "Bitcoin"

        composeTestRule.onNodeWithTag("btnInsert").performClick()
        composeTestRule.onNodeWithTag("btnFiat").performClick()

        composeTestRule.onNodeWithText(expectedText).assertIsDisplayed()
        composeTestRule.onNodeWithText(unExpectedText).assertIsNotDisplayed()

    }

    @Test
    fun test5_search_result() = runTest {

        val searchText = "Ethere"
        val expectedText1 = "Ethereum"
        val expectedText2 = "Ethereum Classic"
        val unExpectedText = "Bitcoin"

        composeTestRule.onNodeWithTag("btnInsert").performClick()
        composeTestRule.onNodeWithTag("btnShowAll").performClick()
        composeTestRule.onNodeWithTag("searchIcon").performClick()
        composeTestRule.onNodeWithTag("searchBox").performTextInput(searchText)

        composeTestRule.onNodeWithText(expectedText1).assertIsDisplayed()
        composeTestRule.onNodeWithText(expectedText2).assertIsDisplayed()
        composeTestRule.onNodeWithText(unExpectedText).assertIsNotDisplayed()

    }




}

