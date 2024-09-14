package com.touhidapps.currency.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.touhidapps.currency.db.entity.CurrencyEntity
import com.touhidapps.currency.enums.ListType
import com.touhidapps.currency.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject public constructor(
    private val repository: CurrencyRepository
) : ViewModel() {

    private val _msgData = MutableStateFlow("")
    val msgDataLive = _msgData.asStateFlow()

    private val _currencyListLive = MutableStateFlow(ArrayList<CurrencyEntity>())
    val currencyListLive = _currencyListLive.asStateFlow()

    private val _showNoDataMessage = MutableStateFlow(false)
    val showNoDataMessage = _showNoDataMessage.asStateFlow()

    fun clearMessageData() {
        _msgData.value = ""
    }

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

                _currencyListLive.value = it as ArrayList

                if (_currencyListLive.value.isEmpty()) {
                    _showNoDataMessage.value = true
                } else {
                    _showNoDataMessage.value = false
                }

            }
        }
    }


}