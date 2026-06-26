package com.joaoneres.uolchallenge.presentation.customerlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.domain.model.Customer
import com.joaoneres.uolchallenge.presentation.componentes.UolTopBar
import com.joaoneres.uolchallenge.presentation.navigation.Routes
import com.joaoneres.uolchallenge.ui.theme.UolChallengeTheme
import org.koin.androidx.compose.koinViewModel


@Composable
fun CustomerListScreen(
    navController: NavController,
    viewModel: CustomerListViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadCustomers()
    }

    Scaffold(
        topBar = {
            UolTopBar(
                title = stringResource(R.string.customer_list_top_bar_title),
                showBackButton = false,
                onBackClick = { navController.popBackStack() }
            )
        }
    ) { paddingValues ->

        when (val state = uiState) {

            CustomerListUiState.Loading -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CustomerListUiState.Success -> {

                CustomerListContent(
                    customers = state.customers,
                    onProfileClick = { profileLink ->
                        navController.navigate(
                            Routes.webViewRoute(profileLink)
                        )
                    },
                    onImageClick = { imageUrl ->
                        navController.navigate(
                            Routes.imageRoute(imageUrl)
                        )
                    },
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is CustomerListUiState.Error -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerListContent(
    customers: List<Customer>,
    onProfileClick: (String) -> Unit,
    onImageClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Text(
            text = stringResource(R.string.customer_list_title),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(
                start = 16.dp,
                top = 16.dp
            )
        )

        Row {
            Text(
                text = stringResource(R.string.customer_list_found),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 4.dp,
                    bottom = 8.dp
                )
            )

            Text(
                text = stringResource(R.string.customer_list_count, customers.size),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(
                    start = 4.dp,
                    top = 4.dp,
                    bottom = 8.dp
                )
            )
        }
        LazyColumn {
            items(customers) { customer ->
                CustomerCard(
                    customer = customer,
                    onProfileClick = onProfileClick,
                    onImageClick = onImageClick
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CustomerListPreview() {
    UolChallengeTheme {
        CustomerListContent(
            customers = listOf(
                Customer(
                    id = "1",
                    name = "João Neres",
                    email = "joao@email.com",
                    phone = null,
                    profileImage = null,
                    profileLink = null,
                    status = "active"
                ),
                Customer(
                    id = "2",
                    name = "Jose Silva",
                    email = "josesilva@email.com",
                    phone = null,
                    profileImage = null,
                    profileLink = null,
                    status = "active"
                )
            ),
            onProfileClick = {},
            onImageClick = {}
        )
    }
}