package com.joaoneres.uolchallenge.presentation.customerlist

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.domain.repository.CustomerRepository
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

    @StringRes
    private fun mapErrorToMessage(
        result: NetworkResult<*>
    ): Int {
        return when (result) {
            is NetworkResult.NetworkError ->
                R.string.error_no_internet

            is NetworkResult.BadRequest ->
                R.string.error_bad_request

            is NetworkResult.Unauthorized ->
                R.string.error_unauthorized

            is NetworkResult.Forbidden ->
                R.string.error_forbidden

            is NetworkResult.NotFound ->
                R.string.error_not_found

            is NetworkResult.ServerError ->
                R.string.error_server

            is NetworkResult.UnknownError ->
                R.string.error_unknown

            is NetworkResult.Success ->
                error("Success não deve ser mapeado como erro")
        }
    }
}
