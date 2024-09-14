package com.touhidapps.currency.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.touhidapps.currency.ui.theme.CurrencyListTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.touhidapps.currency.viewModel.CurrencyViewModel
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.touhidapps.currency.R
import com.touhidapps.currency.db.entity.CurrencyEntity
import com.touhidapps.currency.enums.ListType
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyListScreen(
    navController: NavHostController,
    listType: ListType = ListType.ALL,
    viewModel: CurrencyViewModel = hiltViewModel<CurrencyViewModel>()
) {

    var searchText by remember { mutableStateOf("") }

    val currencyList = viewModel.currencyListLive.collectAsState()
    val showNoDataMessage = viewModel.showNoDataMessage.collectAsState()
    var isSearchFocused by remember { mutableStateOf(false) }

    LaunchedEffect(listType, searchText) {
        viewModel.getCurrencies(listType, searchText)
    }

    CurrencyListTheme {

        Scaffold(

            topBar = {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    actions = {

                    },
                    colors = TopAppBarColors(
                        containerColor = Color.LightGray,
                        scrolledContainerColor = Color.LightGray,
                        navigationIconContentColor = Color.Black,
                        titleContentColor = Color.Black,
                        actionIconContentColor = Color.Black,
                    ),
                    title = {
                        MySearchBar(
                            searchFocusChange = {
                                isSearchFocused = it
                            },
                            searchTextChange = {
                                searchText = it
                            }
                        )
                    } // title

                ) // TopAppBar

            }, // topBar
            content = { innerPadding ->

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (showNoDataMessage.value) {
                        MyNoDataMessage(modifier = Modifier.testTag("noDataMessage"))
                    } else {
                        MyList(currencyList.value, isSearchFocused)
                    }
                }

            },

            ) // Scaffold

    }

}

@ExperimentalMaterial3Api
@Composable
fun MySearchBar(searchFocusChange: (Boolean) -> Unit, searchTextChange: (String) -> Unit) {

    var isSearchBarVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val animatedHeight: Dp by animateDpAsState(
        targetValue = if (isSearchBarVisible) 50.dp else 0.dp,
        animationSpec = tween(durationMillis = 300) // Animation duration
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {

        if (isSearchBarVisible) {
            TextField(
                value = searchText,
                onValueChange = { newText ->
                    searchText = newText
                    searchTextChange(searchText)
                },
                placeholder = {
                    Text(
                        "Search...", color = Color.Gray, style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 15.sp,
                ),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrect = false,
                    imeAction = ImeAction.Search
                ),
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchText.isNotEmpty()) {
                            searchText = "" // Clear the text
                            searchTextChange(searchText)
                        } else {
                            focusManager.clearFocus()
                            isSearchBarVisible = false
                        }
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                    }
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .height(animatedHeight)
                    .clip(RoundedCornerShape(30.dp))
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                        searchFocusChange(isFocused)
                    }
                    .testTag("searchBox"),
            )
        } else {
            // Search Button
            IconButton(
                modifier = Modifier.testTag("searchIcon"),
                onClick = {
                    isSearchBarVisible = true
                }) {
                Icon(Icons.Filled.Search, contentDescription = "Search")
            }
        }
    }

    // After search bar visible
    LaunchedEffect(isSearchBarVisible) {
        if (!isFocused && isSearchBarVisible) {
            focusRequester.requestFocus()
        }
    }


}

@Composable
fun MyList(currencyList: ArrayList<CurrencyEntity>, isSearchMode: Boolean = false) {

    LazyColumn() {

        items(currencyList.size) { item ->
            val mItem = currencyList.get(item)
            RowCurrency("${mItem.name}", "${mItem.code}")
            if (isSearchMode) {
                HorizontalDivider(
                    thickness = 1.dp
                )
            }
        }

    }

}

@Composable
fun MyNoDataMessage(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.ic_no_results),
            contentDescription = "No data found",
            modifier = Modifier.size(80.dp)
        )
        Text("No data found")
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CurrencyListScreenPreview() {
    CurrencyListTheme {
        val navController = rememberNavController()
        CurrencyListScreen(navController)
    }
}


@ExperimentalMaterial3Api
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyNoDataMessagePreview() {
    CurrencyListTheme {
        MyNoDataMessage()
    }
}

