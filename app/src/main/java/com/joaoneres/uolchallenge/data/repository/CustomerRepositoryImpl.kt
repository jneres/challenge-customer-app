package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.mappers.toDomain
import com.joaoneres.uolchallenge.domain.model.Customer
import com.joaoneres.uolchallenge.domain.repository.CustomerRepository
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

private const val NULL_RESPONSE_BODY_MESSAGE = "Response body is null"
private const val HTTP_ERROR_MESSAGE = "Erro HTTP"
class CustomerRepositoryImpl(
    private val customerApi: CustomerApi
) : CustomerRepository {

    override suspend fun getCustomers(): NetworkResult<List<Customer>> {
        return try {
            val response = customerApi.getCustomers()

            if (response.isSuccessful) {
                val body = response.body()
                    ?: return NetworkResult.UnknownError(
                        IllegalStateException(NULL_RESPONSE_BODY_MESSAGE)
                    )

                NetworkResult.Success(
                    body.customers.map { it.toDomain() }
                )
            } else {
                handleHttpError(
                    code = response.code(),
                    message = response.message()
                )
            }

        } catch (e: CancellationException) {
            throw e

        } catch (e: SocketTimeoutException) {
            NetworkResult.NetworkError(e)

        } catch (e: UnknownHostException) {
            NetworkResult.NetworkError(e)

        } catch (e: IOException) {
            NetworkResult.NetworkError(e)

        } catch (e: Exception) {
            NetworkResult.UnknownError(e)
        }
    }

    private fun handleHttpError(
        code: Int,
        message: String
    ): NetworkResult<Nothing> {
        return when (code) {
            HttpURLConnection.HTTP_BAD_REQUEST -> {
                NetworkResult.BadRequest(message)
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> {
                NetworkResult.Unauthorized(message)
            }

            HttpURLConnection.HTTP_FORBIDDEN -> {
                NetworkResult.Forbidden(message)
            }

            HttpURLConnection.HTTP_NOT_FOUND -> {
                NetworkResult.NotFound(message)
            }

            in 500..599 -> {
                NetworkResult.ServerError(
                    code = code,
                    message = message
                )
            }

            else -> {
                NetworkResult.UnknownError(
                    Exception("$HTTP_ERROR_MESSAGE $code")
                )
            }
        }
    }
}