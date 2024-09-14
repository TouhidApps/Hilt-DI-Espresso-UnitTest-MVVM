package com.touhidapps.currency.bkp

import androidx.compose.material3.ExperimentalMaterial3Api
import com.touhidapps.currency.CurrencyViewModelTest
import com.touhidapps.currency.DemoActivityUITest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@ExperimentalMaterial3Api
@RunWith(Suite::class)
@Suite.SuiteClasses(
    CurrencyViewModelTest::class,
    DemoActivityUITest::class
)
class TestSuite