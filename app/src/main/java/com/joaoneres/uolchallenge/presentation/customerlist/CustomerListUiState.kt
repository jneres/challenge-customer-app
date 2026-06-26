package com.joaoneres.uolchallenge.presentation.customerlist

import androidx.annotation.StringRes
import com.joaoneres.uolchallenge.domain.model.Customer

sealed interface CustomerListUiState {

    data object Loading : CustomerListUiState

    data class Success(
        val customers: List<Customer>
    ) : CustomerListUiState

    data class Error(
        @StringRes val messageResId: Int
    ) : CustomerListUiState
}