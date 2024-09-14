package com.touhidapps.currency.repository

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.touhidapps.currency.db.CurrencyDao
import com.touhidapps.currency.db.entity.CurrencyEntity
import com.touhidapps.currency.db.getDemoData
import com.touhidapps.currency.enums.ListType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepository @Inject public constructor(
    private val currencyDao: CurrencyDao
) {

    suspend fun addAllCurrencies(showMsg: (String) -> Unit) {
        try {
            currencyDao.insertCurrency( *getDemoData().toTypedArray() )
            showMsg("Data insert success.")
        } catch (e: SQLiteConstraintException) {
            e.printStackTrace()
            showMsg("This currency ID already exists")
        }
    }

    suspend fun deleteAllCurrencies(showMsg: (String) -> Unit) {
        try {
            currencyDao.deleteAllCurrencies()
            showMsg("Data cleared.")
        } catch (e: Exception) {
            e.printStackTrace()
            showMsg("Something went wrong!")
        }
    }

    suspend fun getCurrencies(
        listType: ListType, searchText: String? = "",
        itemList: (List<CurrencyEntity>) -> Unit
    ) {

        val allData = if (searchText.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                when (listType) {
                    ListType.CRYPTO, ListType.FIAT -> {
                        currencyDao.getCurrenciesByType(listType.name)
                    }

                    ListType.ALL -> {
                        currencyDao.getAllCurrencies()
                    }
                }
            }
        } else {
            val lt = if (listType == ListType.ALL) {
                null // Search from all data
            } else {
                listType.name // Search from crypto or fiat type data
            }
            withContext(Dispatchers.IO) {
                currencyDao.getCurrenciesBySearch(lt, searchText ?: "")
            }
        }

        Log.d("<ALL_DATA>", "loadData: ${allData}")
        itemList(allData)

    } // getAllCurrencies


}