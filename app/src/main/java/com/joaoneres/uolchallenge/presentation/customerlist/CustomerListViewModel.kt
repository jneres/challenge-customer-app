package com.joaoneres.uolchallenge.presentation.customerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoneres.uolchallenge.data.repository.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CustomerListViewModel(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _uiState =
        MutableStateFlow<CustomerListUiState>(
            CustomerListUiState.Loading
        )
    val uiState = _uiState.asStateFlow()

    fun loadCustomers() {
        viewModelScope.launch {
            _uiState.value = CustomerListUiState.Loading

            repository
                .getCustomers()
                .onSuccess { customers ->
                    _uiState.value =
                        CustomerListUiState.Success(customers)
                }
                .onFailure {
                    _uiState.value =
                        CustomerListUiState.Error(
                            it.message.orEmpty()
                        )
                }
        }
    }
}
