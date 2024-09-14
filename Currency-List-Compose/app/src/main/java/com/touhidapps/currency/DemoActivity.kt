package com.touhidapps.currency

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.ui.ActivityDemoScreen
import com.touhidapps.currency.ui.CurrencyListScreen
import com.touhidapps.currency.ui.theme.CurrencyListTheme
import com.touhidapps.currency.utils.AppRoute
import com.touhidapps.currency.utils.Const
import com.touhidapps.currency.viewModel.CurrencyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@AndroidEntryPoint
class DemoActivity : ComponentActivity() {

    private val currencyViewModel: CurrencyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation()
                }
            }
        }

    } // onCreate

} // DemoActivity



@ExperimentalMaterial3Api
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppRoute.DemoHome.route) {
        composable(AppRoute.DemoHome.route) { ActivityDemoScreen(navController) }
        composable(AppRoute.CryptoList.route) { backStackEntry ->
            val listType = backStackEntry.arguments?.getString("listType") ?: ""
            CurrencyListScreen(navController, ListType.fromString(listType))
        }
    }
}