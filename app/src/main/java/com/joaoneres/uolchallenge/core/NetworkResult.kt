package com.joaoneres.uolchallenge.core

sealed interface NetworkResult<out T> {

    data class Success<out T>(
        val data: T
    ) : NetworkResult<T>

    data class BadRequest(
        val message: String?
    ) : NetworkResult<Nothing>

    data class Unauthorized(
        val message: String?
    ) : NetworkResult<Nothing>

    data class Forbidden(
        val message: String?
    ) : NetworkResult<Nothing>

    data class NotFound(
        val message: String?
    ) : NetworkResult<Nothing>

    data class ServerError(
        val code: Int,
        val message: String?
    ) : NetworkResult<Nothing>

    data class NetworkError(
        val exception: Throwable
    ) : NetworkResult<Nothing>

    data class UnknownError(
        val exception: Throwable
    ) : NetworkResult<Nothing>
}