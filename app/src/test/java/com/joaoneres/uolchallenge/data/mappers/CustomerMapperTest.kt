package com.joaoneres.uolchallenge.data.mappers

import com.joaoneres.uolchallenge.util.TestDataFactory
import junit.framework.TestCase.assertEquals
import org.junit.Test

class CustomerMapperTest {

    @Test
    fun `should map CustomerDto to Customer`() {

        val dto = TestDataFactory.createCustomerResponse()

        val customer = dto.toDomain()

        assertEquals(dto.id, customer.id)
        assertEquals(dto.name, customer.name)
        assertEquals(dto.email, customer.email)
        assertEquals(dto.phone, customer.phone)
        assertEquals(dto.profileImage, customer.profileImage)
        assertEquals(dto.profileLink, customer.profileLink)
        assertEquals(dto.status, customer.status)
    }
}
