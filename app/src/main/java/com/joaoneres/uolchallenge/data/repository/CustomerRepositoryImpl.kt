package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.model.Customer

class CustomerRepositoryImpl(
    private val customerApi: CustomerApi
) : CustomerRepository {

    override suspend fun getCustomers(): Result<List<Customer>> {
        return try {
            val response = customerApi.getCustomers()

            if (response.isSuccessful) {
                Result.success(
                    response.body()?.customers.orEmpty()
                )
            } else {
                Result.failure(
                    Exception("Erro HTTP ${response.code()}")
                )
            }
        } catch (e: Exception) {
            Result.failure(
                Exception(
                    "Erro ao carregar clientes", e
                )
            )
        }
    }
}
