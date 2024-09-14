package com.touhidapps.currency.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.touhidapps.currency.db.DbConstants

@Entity(tableName = DbConstants.TABLE_CURRENCY)
data class CurrencyEntity(
    @PrimaryKey(autoGenerate = false) val id: String = "",
    @ColumnInfo(name = "type") val type: String? = "",
    @ColumnInfo(name = "name") val name: String? = "",
    @ColumnInfo(name = "symbol") val symbol: String? = "",
    @ColumnInfo(name = "code") val code: String? = ""
)
