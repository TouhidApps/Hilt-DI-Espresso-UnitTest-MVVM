package com.touhidapps.currency.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.ui.theme.CurrencyListTheme
import com.touhidapps.currency.utils.AppRoute
import com.touhidapps.currency.viewModel.CurrencyViewModel


@Composable
fun ActivityDemoScreen(
    navController: NavHostController,
    viewModel: CurrencyViewModel = hiltViewModel()
) {

    var dialogDelete by remember { mutableStateOf(false) }

    val errMsg by viewModel.msgDataLive.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(errMsg) {
        if (!errMsg.isNullOrEmpty()) {
            Toast.makeText(context, errMsg, Toast.LENGTH_SHORT).show()
            viewModel.clearMessageData()
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.testTag("btnClearAll"),
            onClick = {
                dialogDelete = true
            }) {
            Text("Clear All")
        }

        Button(
            modifier = Modifier.testTag("btnInsert"),
            onClick = {
            viewModel.addAllCurrencies()
        }) {
            Text("Insert Data")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier.testTag("btnCrypto"),
            onClick = {
            navController.navigate(AppRoute.CryptoList.createRoute(ListType.CRYPTO))
        }) {
            Text("Crypto List")
        }

        Button(
            modifier = Modifier.testTag("btnFiat"),
            onClick = {
            navController.navigate(AppRoute.CryptoList.createRoute(ListType.FIAT))
        }) {
            Text("Fiat List")
        }

        Button(
            modifier = Modifier.testTag("btnShowAll"),
            onClick = {
            navController.navigate(AppRoute.CryptoList.createRoute(ListType.ALL))
        }) {
            Text("Show All")
        }

    }

    if (dialogDelete) {
        AlertDialog(
            onDismissRequest = { dialogDelete = false },
            title = { Text("Alert!") },
            text = { Text("Do you want to delete all data from database?") },
            confirmButton = {
                Button(onClick = {
                    dialogDelete = false
                    viewModel.deleteAllCurrencies()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = {
                    dialogDelete = false
                }) {
                    Text("No")
                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ActivityDemoScreenPreview() {
    CurrencyListTheme {
        val navController = rememberNavController()
        ActivityDemoScreen(navController)
    }
}