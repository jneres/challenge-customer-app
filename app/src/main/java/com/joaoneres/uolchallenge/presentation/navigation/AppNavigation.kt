package com.joaoneres.uolchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joaoneres.uolchallenge.presentation.customerlist.CustomerListScreen
import com.joaoneres.uolchallenge.presentation.image.ImageScreen
import com.joaoneres.uolchallenge.presentation.webview.WebViewScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.CUSTOMER_LIST
    ) {

        composable(Routes.CUSTOMER_LIST) {
            CustomerListScreen(
                navController = navController
            )
        }

        composable(
            route = Routes.IMAGE
        ) { backStackEntry ->

            val imageUrl = backStackEntry.arguments
                    ?.getString("imageUrl").orEmpty()

            ImageScreen(
                navController = navController,
                imageUrl = imageUrl
            )
        }

        composable(
            route = Routes.WEBVIEW
        ) { backStackEntry ->

            val url = backStackEntry.arguments
                ?.getString("url").orEmpty()

            WebViewScreen(
                navController,
                url = url
            )
        }
    }
}