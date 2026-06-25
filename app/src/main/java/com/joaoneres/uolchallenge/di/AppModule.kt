package com.joaoneres.uolchallenge.di

import com.joaoneres.uolchallenge.core.network.NetworkModule
import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.repository.CustomerRepository
import com.joaoneres.uolchallenge.data.repository.CustomerRepositoryImpl
import com.joaoneres.uolchallenge.presentation.customerlist.CustomerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<CustomerApi> {
        NetworkModule.customerApi
    }

    single<CustomerRepository> {
        CustomerRepositoryImpl(get())
    }

    viewModel {
        CustomerListViewModel(get())
    }
}