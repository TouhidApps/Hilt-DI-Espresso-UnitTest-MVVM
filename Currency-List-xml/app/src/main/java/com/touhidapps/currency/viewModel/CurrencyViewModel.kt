package com.touhidapps.currency.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touhidapps.currency.db.entity.CurrencyEntity
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject public constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _msgData = MutableLiveData<String>()
    val msgDataLive: LiveData<String> get() = _msgData

    private val _currencyListLive = MutableLiveData<List<CurrencyEntity>>()
    val currencyListLive: LiveData<List<CurrencyEntity>> get() = _currencyListLive

    fun addAllCurrencies() {
        viewModelScope.launch {
            repository.addAllCurrencies {
                _msgData.value = it
            }
        }
    }

    fun deleteAllCurrencies() {
        viewModelScope.launch {
            repository.deleteAllCurrencies {
                _msgData.value = it
            }
        }
    }

    fun getCurrencies(listType: ListType, searchText: String? = "") {
        viewModelScope.launch {
            repository.getCurrencies(listType, searchText) {
                _currencyListLive.value = it
            }
        }
    }


}