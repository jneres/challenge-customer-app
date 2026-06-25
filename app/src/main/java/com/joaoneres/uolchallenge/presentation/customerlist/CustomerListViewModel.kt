package com.joaoneres.uolchallenge.presentation.customerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoneres.uolchallenge.core.NetworkResult
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

            val result = repository.getCustomers()

            _uiState.value = when (result) {
                is NetworkResult.Success ->
                    CustomerListUiState.Success(result.data)
                else ->
                    CustomerListUiState.Error(mapErrorToMessage(result))
            }
        }
    }

    private fun mapErrorToMessage(result: NetworkResult<*>): String {
        return when (result) {
            is NetworkResult.NetworkError -> "Sem conexão com a internet"
            is NetworkResult.BadRequest -> result.message ?: "Erro na requisição"
            else -> "Ocorreu um erro inesperado"
        }
    }
}
