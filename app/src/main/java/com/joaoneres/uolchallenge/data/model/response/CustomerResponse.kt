package com.joaoneres.uolchallenge.data.model.response

data class CustomerResponse(
    val id: String,
    val name: String?,
    val email: String?,
    val phone: String?,
    val profileImage: String?,
    val profileLink: String?,
    val status: String?
)
