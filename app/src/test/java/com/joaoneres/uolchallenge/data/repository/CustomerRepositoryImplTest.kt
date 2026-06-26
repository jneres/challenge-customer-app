package com.joaoneres.uolchallenge.data.repository

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.data.api.CustomerApi
import com.joaoneres.uolchallenge.data.model.response.BaseCustomersResponse
import com.joaoneres.uolchallenge.util.TestDataFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

class CustomerRepositoryImplTest {

    private val api: CustomerApi = mockk()
    private lateinit var repository: CustomerRepositoryImpl
    private val customerDto = TestDataFactory.createCustomerResponse()

    @Before
    fun setup() {
        repository = CustomerRepositoryImpl(api)
    }

    @Test
    fun `should return Success when API responds with 200`() = runTest {

        val response = Response.success(
            BaseCustomersResponse(
                customers = listOf(customerDto)
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

        val response = Response.error<BaseCustomersResponse>(
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

        val response = Response.success<BaseCustomersResponse>(null)

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

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


    @Test
    fun `should return Unauthorized when API returns 401`() = runTest {

        val response = Response.error<BaseCustomersResponse>(
            401,
            "".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.Unauthorized)
    }

    @Test
    fun `should return Forbidden when API returns 403`() = runTest {

        val response = Response.error<BaseCustomersResponse>(
            403,
            "".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.Forbidden)
    }

    @Test
    fun `should return NotFound when API returns 404`() = runTest {
        // given
        val response = Response.error<BaseCustomersResponse>(
            404,
            "".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getCustomers()
        } returns response

        // when
        val result = repository.getCustomers()

        // then
        assertTrue(result is NetworkResult.NotFound)
    }

    @Test
    fun `should return ServerError when API returns 500`() = runTest {

        val response = Response.error<BaseCustomersResponse>(
            500,
            "".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.ServerError)

        result as NetworkResult.ServerError

        assertEquals(500, result.code)
    }

    @Test
    fun `should return UnknownError when API returns an unmapped HTTP error`() = runTest {

        val response = Response.error<BaseCustomersResponse>(
            418,
            "".toResponseBody("application/json".toMediaType())
        )

        coEvery {
            api.getCustomers()
        } returns response

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.UnknownError)
    }

    @Test
    fun `should return NetworkError when SocketTimeoutException occurs`() = runTest {

        coEvery {
            api.getCustomers()
        } throws SocketTimeoutException()

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `should return NetworkError when IOException occurs`() = runTest {

        coEvery {
            api.getCustomers()
        } throws IOException()

        val result = repository.getCustomers()

        assertTrue(result is NetworkResult.NetworkError)
    }

    @Test
    fun `should rethrow CancellationException when request is cancelled`() {
        coEvery {
            api.getCustomers()
        } throws CancellationException("Request cancelled")

        assertThrows(CancellationException::class.java) {
            runTest {
                repository.getCustomers()
            }
        }
    }
}
