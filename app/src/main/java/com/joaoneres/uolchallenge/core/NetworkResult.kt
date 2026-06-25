package com.joaoneres.uolchallenge.core

sealed interface NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>
    data class BadRequest(val message: String?) : NetworkResult<Nothing> // Erro 400
    data class NetworkError(val exception: Throwable) : NetworkResult<Nothing> // Sem internet / Timeout
    data class UnknownError(val exception: Throwable) : NetworkResult<Nothing> // Outros erros (ex: 500)
}