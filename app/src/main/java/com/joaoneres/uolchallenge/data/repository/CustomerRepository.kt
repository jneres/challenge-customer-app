package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.data.model.Customer

interface CustomerRepository {
    suspend fun getCustomers(): Result<List<Customer>>
}