package com.joaoneres.uolchallenge.data.api

import com.joaoneres.uolchallenge.data.model.response.BaseCustomersResponse
import retrofit2.Response
import retrofit2.http.GET

interface CustomerApi {

    @GET("service.json")
    suspend fun getCustomers(): Response<BaseCustomersResponse>
}