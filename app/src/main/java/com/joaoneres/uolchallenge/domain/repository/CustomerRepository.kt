package com.joaoneres.uolchallenge.domain.repository

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.domain.model.Customer

interface CustomerRepository {
    suspend fun getCustomers(): NetworkResult<List<Customer>>
}