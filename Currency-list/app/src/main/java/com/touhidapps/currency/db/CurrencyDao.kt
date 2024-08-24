package com.touhidapps.currency.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.touhidapps.currency.db.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM ${DbConstants.TABLE_CURRENCY}")
    suspend fun getAllCurrencies(): List<CurrencyEntity>

    @Query("SELECT * FROM ${DbConstants.TABLE_CURRENCY} WHERE type = :type")
    suspend fun getCurrenciesByType(type: String): List<CurrencyEntity>

    @Query("SELECT * FROM ${DbConstants.TABLE_CURRENCY} WHERE (:type IS NULL OR type = :type) AND (name LIKE :searchText || '%' OR name LIKE '% ' || :searchText || '%' OR symbol LIKE :searchText || '%')")
    suspend fun getCurrenciesBySearch(type: String?, searchText: String): List<CurrencyEntity>

    @Insert
    suspend fun insertCurrency(vararg currencies: CurrencyEntity)

    @Query("DELETE FROM ${DbConstants.TABLE_CURRENCY}")
    suspend fun deleteAllCurrencies()

}