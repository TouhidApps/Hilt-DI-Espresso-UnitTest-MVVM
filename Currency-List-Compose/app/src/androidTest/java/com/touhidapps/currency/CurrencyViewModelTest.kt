package com.touhidapps.currency

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.touhidapps.currency.db.AppDatabase
import com.touhidapps.currency.db.CurrencyDao
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.repository.CurrencyRepository
import com.touhidapps.currency.ui.theme.CurrencyListTheme
import com.touhidapps.currency.viewModel.CurrencyViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@ExperimentalMaterial3Api
@HiltAndroidTest
class CurrencyViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var appDatabase: AppDatabase

    private lateinit var currencyDao: CurrencyDao
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var viewModel: CurrencyViewModel

    @Before
    fun setup() {
        // Initialize the Hilt rule
        hiltRule.inject()

        currencyDao = appDatabase.currencyDao()
        currencyRepository = CurrencyRepository(currencyDao)

        // Initialize ViewModel
        viewModel = CurrencyViewModel(currencyRepository)
    }

    @Test
    fun insertDataToDb() = runTest {

        // Arrange
        val expectedData = "Data insert success."

        val job = launch {
            withTimeout(5000) {
                viewModel.msgDataLive.collect { value ->

                    if (value.isNotEmpty()) { // If this logic is false always then the process will not stop. so use withTimeout(5000){}
                        // The test will continue only when expectedData is received
                        // Assert
                        assertEquals(expectedData, value)
                        cancel()
                    }
                }
            }
        }

        // Act
        viewModel.addAllCurrencies()

        job.join()

    } // insertDataToDb

    @Test
    fun deleteDataFromDb() = runTest {

        // Arrange
        val expectedData1 = "Data insert success."
        val expectedData2 = "Data cleared."

        val job = launch {
            withTimeout(5000) {
                viewModel.msgDataLive.collect { value ->
                    if (value.isNotEmpty()) {
                        // The test will continue only when expectedData is received
                        // Assert
                        if (value == expectedData1) {
                            assertEquals(expectedData1, value)
                        } else if (value == expectedData2) {
                            assertEquals(expectedData2, value)
                            cancel()
                        } else {
                            assertEquals(1, 2)
                            cancel()
                        }
                    }
                }
            }
        }

        // Act
        viewModel.addAllCurrencies() // First add and then delete
        viewModel.deleteAllCurrencies()

        job.join()

    } // deleteDataFromDb

    @Test
    fun getAllDataFromDb() = runTest {

        // Arrange
        val minItemCount = 10

        val job = launch {
            withTimeout(5000) {
                viewModel.currencyListLive.collect { value ->
                    if (value.isNotEmpty()) {
                        // The test will continue only when expectedData is received
                        // Assert
                        assertTrue(value.size > minItemCount)
                        cancel()
                    }
                }
            }
        }

        // Act
        viewModel.addAllCurrencies() // First add and then delete
        viewModel.getCurrencies(ListType.ALL, "")

        job.join()

    } // getAllDataFromDb


}

