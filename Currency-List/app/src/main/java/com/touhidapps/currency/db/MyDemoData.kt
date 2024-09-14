package com.touhidapps.currency.db

import com.touhidapps.currency.db.entity.CurrencyEntity
import com.touhidapps.currency.enums.ListType


fun getDemoData(): ArrayList<CurrencyEntity> {
    return arrayListOf(
        // Crypto
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "BTC",
            name = "Bitcoin",
            symbol = "BTC"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "ETH",
            name = "Ethereum",
            symbol = "ETH"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "XRP",
            name = "XRP",
            symbol = "XRP"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "BCH",
            name = "Bitcoin Cash",
            symbol = "BCH"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "LTC",
            name = "Litecoin",
            symbol = "LTC"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "EOS",
            name = "EOS",
            symbol = "EOS"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "BNB",
            name = "Binance Coin",
            symbol = "BNB"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "LINK",
            name = "Chainlink",
            symbol = "LINK"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "NEO",
            name = "NEO",
            symbol = "NEO"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "ETC",
            name = "Ethereum Classic",
            symbol = "ETC"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "ONT",
            name = "Ontology",
            symbol = "ONT"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "CRO",
            name = "Crypto.com Chain",
            symbol = "CRO"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "CUC",
            name = "Cucumber",
            symbol = "CUC"
        ),
        CurrencyEntity(
            type = ListType.CRYPTO.name,
            id = "USDC",
            name = "USD Coin",
            symbol = "USDC"
        ),

        // Fiat
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "SGD",
            name = "Singapore Dollar",
            symbol = "$",
            code = "SGD"
        ),
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "EUR",
            name = "Euro",
            symbol = "€",
            code = "EUR"
        ),
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "GBP",
            name = "British Pound",
            symbol = "£",
            code = "GBP"
        ),
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "HKD",
            name = "Hong Kong Dollar",
            symbol = "$",
            code = "HKD"
        ),
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "JPY",
            name = "Japanese Yen",
            symbol = "¥",
            code = "JPY"
        ),
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "AUD",
            name = "Australian Dollar",
            symbol = "$",
            code = "AUD"
        ),
        CurrencyEntity(
            type = ListType.FIAT.name,
            id = "USD",
            name = "United States Dollar",
            symbol = "$",
            code = "USD"
        )
    )
}