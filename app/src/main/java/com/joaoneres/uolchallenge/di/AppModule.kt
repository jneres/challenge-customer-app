package com.joaoneres.uolchallenge.di

import com.joaoneres.uolchallenge.domain.repository.CustomerRepository
import com.joaoneres.uolchallenge.data.repository.CustomerRepositoryImpl
import com.joaoneres.uolchallenge.presentation.customerlist.CustomerListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    factory<CustomerRepository> {
        CustomerRepositoryImpl(get())
    }

    viewModelOf(::CustomerListViewModel)
}