package com.joaoneres.uolchallenge.data.dto

data class CustomerDto(
    val id: String,
    val name: String?,
    val email: String?,
    val phone: String?,
    val profileImage: String?,
    val profileLink: String?,
    val status: String?
)
