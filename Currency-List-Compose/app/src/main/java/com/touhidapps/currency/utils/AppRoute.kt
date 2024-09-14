package com.touhidapps.currency.utils

import com.touhidapps.currency.enums.ListType

sealed class AppRoute(val route: String) {

    object DemoHome: AppRoute("home")
//    object CryptoList: AppRoute("crypto_list")

    object CryptoList : AppRoute("crypto_list/{listType}") {
        fun createRoute(listType: ListType) = "crypto_list/${listType.name}"
//        fun createRoute(id: String) = "crypto_list/$id"
    }
}