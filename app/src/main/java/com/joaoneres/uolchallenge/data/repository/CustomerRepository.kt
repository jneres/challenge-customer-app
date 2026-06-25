package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.domain.model.Customer

interface CustomerRepository {
    suspend fun getCustomers(): NetworkResult<List<Customer>>
}