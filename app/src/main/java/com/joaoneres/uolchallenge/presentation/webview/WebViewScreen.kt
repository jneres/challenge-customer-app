package com.joaoneres.uolchallenge.presentation.webview

import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.presentation.componentes.UolTopBar


@Composable
fun WebViewScreen(
    navController: NavController,
    url: String
) {

    var hasError by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            UolTopBar(
                title = stringResource(R.string.web_view_screen_title),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->


        if (hasError) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                ErrorComponent(
                    message = stringResource(R.string.web_view_screen_error_message),
                    onRetry = {
                        hasError = false
                    }
                )
            }


        } else {

            AndroidView(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),

                factory = { context ->
                    WebView(context).apply {

                        webViewClient = object : WebViewClient() {

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                super.onReceivedError(view, request, error)

                                if (request?.isForMainFrame == true) {
                                    hasError = true
                                }
                            }

                            override fun onReceivedHttpError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                errorResponse: WebResourceResponse?
                            ) {
                                super.onReceivedHttpError(view, request, errorResponse)

                                if (request?.isForMainFrame == true) {
                                    hasError = true
                                }
                            }
                        }

                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true

                        loadUrl(url)
                    }
                }
            )
        }
    }
}