package com.touhidapps.currency.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.touhidapps.currency.R
import com.touhidapps.currency.ui.theme.CurrencyListTheme


@Composable
fun RowCurrency(
    title: String = "-",
    code: String = ""
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(shape = RoundedCornerShape(50))
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${title.first()}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        } // Box round

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "${title}",
            color = Color.DarkGray,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(1F))

        Text(
            text = "${code}",
            color = Color.DarkGray,
            style = MaterialTheme.typography.titleMedium
        )

        Image(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_right_24),
            contentDescription = "Arrow",
            modifier = Modifier.size(30.dp)
        )

    } // Row

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RowCurrencyPreview() {
    CurrencyListTheme {
        RowCurrency("Bitcoin Cash", "BCH")
    }
}