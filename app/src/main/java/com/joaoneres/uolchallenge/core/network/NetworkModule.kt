package com.joaoneres.uolchallenge.core.network

import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.repository.CustomerRepository
import com.joaoneres.uolchallenge.data.repository.CustomerRepositoryImpl
import com.joaoneres.uolchallenge.presentation.customerlist.CustomerListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com/newloran2/testApp2026/main/")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<CustomerApi> {
        get<Retrofit>().create(CustomerApi::class.java)
    }

    single<CustomerRepository> {
        CustomerRepositoryImpl(get())
    }

    viewModel {
        CustomerListViewModel(get())
    }
}