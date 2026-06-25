package com.joaoneres.uolchallenge.presentation.customerlist

import com.joaoneres.uolchallenge.core.NetworkResult
import com.joaoneres.uolchallenge.data.repository.CustomerRepository
import com.joaoneres.uolchallenge.domain.model.Customer
import com.joaoneres.uolchallenge.util.MainDispatcherRule
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
        Customer(
            id = "1",
            name = "João",
            email = "joao@email.com",
            phone = "11999999999",
            profileImage = "image",
            profileLink = "link",
            status = "ACTIVE"
        )
    )

    @Before
    fun setup() {
        viewModel = CustomerListViewModel(repository)
    }

    @Test
    fun `should return Success state when repository returns Success`() = runTest {
        // given
        coEvery {
            repository.getCustomers()
        } returns NetworkResult.Success(customers)

        // when
        viewModel.loadCustomers()
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Success)

        state as CustomerListUiState.Success

        assertEquals(1, state.customers.size)
        assertEquals("João", state.customers.first().name)
    }

    @Test
    fun `should return Error state when repository returns NetworkError`() = runTest {
        // given
        coEvery {
            repository.getCustomers()
        } returns NetworkResult.NetworkError(UnknownHostException())

        // when
        viewModel.loadCustomers()
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals("Sem conexão com a internet", state.message)
    }

    @Test
    fun `should return Error state when repository returns BadRequest`() = runTest {
        // given
        coEvery {
            repository.getCustomers()
        } returns NetworkResult.BadRequest("Erro 400")

        // when
        viewModel.loadCustomers()
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals("Erro 400", state.message)
    }

    @Test
    fun `should return default BadRequest message when message is null`() = runTest {
        // given
        coEvery {
            repository.getCustomers()
        } returns NetworkResult.BadRequest(null)

        // when
        viewModel.loadCustomers()
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals("Erro na requisição", state.message)
    }

    @Test
    fun `should return Error state when repository returns UnknownError`() = runTest {
        // given
        coEvery {
            repository.getCustomers()
        } returns NetworkResult.UnknownError(RuntimeException())

        // when
        viewModel.loadCustomers()
        advanceUntilIdle()

        // then
        val state = viewModel.uiState.value

        assertTrue(state is CustomerListUiState.Error)

        state as CustomerListUiState.Error

        assertEquals("Ocorreu um erro inesperado", state.message)
    }

    @Test
    fun `should start with Loading state`() {

        assertTrue(
            viewModel.uiState.value is CustomerListUiState.Loading
        )
    }
}