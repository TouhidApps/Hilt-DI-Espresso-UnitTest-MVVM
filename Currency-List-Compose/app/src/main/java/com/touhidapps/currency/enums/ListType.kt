package com.touhidapps.currency.enums

enum class ListType {
    CRYPTO,
    FIAT,
    ALL;

    companion object {
        fun fromString(value: String): ListType {
            return values().find { it.name.equals(value, ignoreCase = true) } ?: ALL
        }
    }

}