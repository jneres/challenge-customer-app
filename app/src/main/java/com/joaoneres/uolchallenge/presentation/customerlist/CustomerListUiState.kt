package com.joaoneres.uolchallenge.presentation.customerlist

import com.joaoneres.uolchallenge.data.model.Customer

sealed interface CustomerListUiState {

    data object Loading : CustomerListUiState

    data class Success(
        val customers: List<Customer>
    ) : CustomerListUiState

    data class Error(
        val message: String
    ) : CustomerListUiState
}