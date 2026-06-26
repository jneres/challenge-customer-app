package com.joaoneres.uolchallenge.util

import com.joaoneres.uolchallenge.data.model.response.CustomerResponse
import com.joaoneres.uolchallenge.domain.model.Customer

object TestDataFactory {

    fun createCustomer(
        id: String = "1",
        name: String = "João",
        email: String = "joao@email.com",
        phone: String? = "11999999999",
        profileImage: String? = "image",
        profileLink: String? = "link",
        status: String = "ACTIVE"
    ) = Customer(
        id = id,
        name = name,
        email = email,
        phone = phone,
        profileImage = profileImage,
        profileLink = profileLink,
        status = status
    )

    fun createCustomerResponse(
        id: String = "1",
        name: String = "João",
        email: String = "joao@email.com",
        phone: String? = "11999999999",
        profileImage: String? = "image",
        profileLink: String? = "link",
        status: String = "ACTIVE"
    ) = CustomerResponse(
        id = id,
        name = name,
        email = email,
        phone = phone,
        profileImage = profileImage,
        profileLink = profileLink,
        status = status
    )
}