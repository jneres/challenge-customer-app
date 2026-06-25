package com.joaoneres.uolchallenge.data.mappers

import com.joaoneres.uolchallenge.data.dto.CustomerDto
import com.joaoneres.uolchallenge.domain.model.Customer

fun CustomerDto.toCustomer(): Customer {
    return Customer(
        id = id.orEmpty(),
        name = name,
        email = email,
        phone = phone,
        profileImage = profileImage,
        profileLink = profileLink,
        status = status
    )
}