package com.joaoneres.uolchallenge.data.mappers

import com.joaoneres.uolchallenge.data.model.dto.CustomerDto
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CustomerMapperTest {

    @Test
    fun `should map CustomerDto to Customer`() {

        val dto = CustomerDto(
            id = "1",
            name = "João",
            email = "joao@email.com",
            phone = "11999999999",
            profileImage = "image.jpg",
            profileLink = "https://github.com",
            status = "active"
        )

        val customer = dto.toCustomer()

        assertEquals(dto.id, customer.id)
        assertEquals(dto.name, customer.name)
        assertEquals(dto.email, customer.email)
        assertEquals(dto.phone, customer.phone)
        assertEquals(dto.profileImage, customer.profileImage)
        assertEquals(dto.profileLink, customer.profileLink)
        assertEquals(dto.status, customer.status)
    }
}
