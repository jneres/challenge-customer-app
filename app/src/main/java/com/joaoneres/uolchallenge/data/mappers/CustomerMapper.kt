package com.joaoneres.uolchallenge.data.mappers

import com.joaoneres.uolchallenge.data.model.response.CustomerResponse
import com.joaoneres.uolchallenge.domain.model.Customer

fun CustomerResponse.toDomain(): Customer {
    return Customer(
        id = id,
        name = name,
        email = email,
        phone = phone,
        profileImage = profileImage,
        profileLink = profileLink,
        status = status
    )
}