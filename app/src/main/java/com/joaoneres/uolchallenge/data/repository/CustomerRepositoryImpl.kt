package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.mappers.toDomain
import com.joaoneres.uolchallenge.domain.model.Customer
import com.joaoneres.uolchallenge.domain.repository.CustomerRepository
import java.net.UnknownHostException

class CustomerRepositoryImpl(
    private val customerApi: CustomerApi
) : CustomerRepository {

    override suspend fun getCustomers(): NetworkResult<List<Customer>> {
        return try {

            val response = customerApi.getCustomers()

            if (response.isSuccessful) {

                val body = response.body()
                    ?: return NetworkResult.UnknownError(
                        IllegalStateException("Response body is null")
                    )

                val customers = body.customers.map { it.toDomain() }

                NetworkResult.Success(customers)

            } else {

                if (response.code() == 400) {
                    NetworkResult.BadRequest(response.message())
                } else {
                    NetworkResult.UnknownError(
                        Exception("Erro HTTP ${response.code()}")
                    )
                }
            }

        } catch (e: UnknownHostException) {

            NetworkResult.NetworkError(e)

        } catch (e: Exception) {

            NetworkResult.UnknownError(e)

        }
    }
}