package com.joaoneres.uolchallenge.data.api

import com.joaoneres.uolchallenge.data.model.CustomersResponse
import retrofit2.Response
import retrofit2.http.GET

interface CustomerApi {

    @GET("service.json")
    suspend fun getCustomers(): Response<CustomersResponse>
}