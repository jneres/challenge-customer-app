package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.model.dto.CustomerDto
import com.joaoneres.uolchallenge.data.dto.CustomersResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException

class CustomerRepositoryImplTest {

    private val api: CustomerApi = mockk()
    private lateinit var repository: CustomerRepositoryImpl

    private val customerDto  = CustomerDto(
        id = "1",
        name = "João",
        email = "joao@email.com",
        phone = "11999999999",
        profileImage = "image",
        profileLink = "link",
        status = "ACTIVE"
    )

    @Before
    fun setup() {
        repository = CustomerRepositoryImpl(api)
    }

    @Test
    fun `should return Success when API responds with 200`() = runTest {

        val response = Response.success(
            CustomersResponse(
                customers = listOf(customerDto )
            )
        )

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.Success)

        result as NetworkResult.Success

        assertEquals(1, result.data.size)
        assertEquals("João", result.data.first().name)
    }

    @Test
    fun `should return BadRequest when API returns 400`() = runTest {

        val response = Response.error<CustomersResponse>(
            400,
            "".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.BadRequest)
    }

    @Test
    fun `should return UnknownError when response body is null`() = runTest {
        // given
        val response = Response.success<CustomersResponse>(null)

        coEvery {
            api.getCustomers()
        } returns response

        // when
        val result = repository.getCustomers()

        // then
        assertTrue(result is NetworkResult.UnknownError)
    }

    @Test
    fun `should return NetworkError when UnknownHostException occurs`() = runTest {
        coEvery {
            api.getCustomers()
        } throws UnknownHostException()

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `should return UnknownError when an unexpected exception occurs`() = runTest {
        coEvery {
            api.getCustomers()
        } throws RuntimeException("Unexpected error")

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.UnknownError)
    }

}
