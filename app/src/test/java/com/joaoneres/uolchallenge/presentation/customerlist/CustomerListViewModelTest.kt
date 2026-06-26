package com.joaoneres.uolchallenge.presentation.customerlist

import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.domain.repository.CustomerRepository
import com.joaoneres.uolchallenge.util.MainDispatcherRule
import com.joaoneres.uolchallenge.util.TestDataFactory
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
class CustomerListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository: CustomerRepository = mockk()
    private lateinit var viewModel: CustomerListViewModel

    private val customers = listOf(
        TestDataFactory.createCustomer()
    )

    @Before
    fun setup() {
        viewModel = CustomerListViewModel(repository)
    }

    @Test
    fun `should return Success state when repository returns Success`() = runTest {

        coEvery {
            repository.getCustomers()
        } returns NetworkResult.Success(customers)

        viewModel.loadCustomers()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Success)

        state as CustomerListUiState.Success

        assertEquals(1, state.customers.size)
        assertEquals("João", state.customers.first().name)
    }

    @Test
    fun `should return Error state when repository returns NetworkError`() = runTest {

        coEvery {
            repository.getCustomers()
        } returns NetworkResult.NetworkError(UnknownHostException())

        viewModel.loadCustomers()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals(R.string.error_no_internet, state.messageResId)
    }

    @Test
    fun `should return Error state when repository returns BadRequest`() = runTest {

        coEvery {
            repository.getCustomers()
        } returns NetworkResult.BadRequest("Erro 400")

        viewModel.loadCustomers()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals(R.string.error_bad_request, state.messageResId)
    }

    @Test
    fun `should return default BadRequest message when message is null`() = runTest {

        coEvery {
            repository.getCustomers()
        } returns NetworkResult.BadRequest(null)

        viewModel.loadCustomers()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals(R.string.error_bad_request, state.messageResId)
    }

    @Test
    fun `should return Error state when repository returns UnknownError`() = runTest {

        coEvery {
            repository.getCustomers()
        } returns NetworkResult.UnknownError(RuntimeException())

        viewModel.loadCustomers()
        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals(R.string.error_unknown, state.messageResId)
    }

    @Test
    fun `should start with Loading state`() {

        assertTrue(
            viewModel.uiState.value is CustomerListUiState.Loading
        )
    }
}