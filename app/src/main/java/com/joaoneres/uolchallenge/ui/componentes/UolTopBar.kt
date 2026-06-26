package com.joaoneres.uolchallenge.ui.componentes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.ui.theme.UolOrange
import com.joaoneres.uolchallenge.ui.theme.UolWhite

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UolTopBar(
    title: String,
    showBackButton: Boolean = true,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        title = {
            Text(title)
        },
        navigationIcon = {
            if (showBackButton) {

                onBackClick?.let {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.uol_top_bar_go_back)
                        )
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = UolOrange,
            titleContentColor = UolWhite,
            navigationIconContentColor = UolWhite
        )
    )

}