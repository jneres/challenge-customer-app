package com.joaoneres.uolchallenge.domain.model

data class Customer(
    val id: String,
    val name: String?,
    val email: String?,
    val phone: String?,
    val profileImage: String?,
    val profileLink: String?,
    val status: String?
)