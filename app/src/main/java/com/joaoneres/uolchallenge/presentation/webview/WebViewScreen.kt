package com.joaoneres.uolchallenge.presentation.webview

import android.graphics.Bitmap
import android.net.http.SslError
import android.webkit.CookieManager
import android.webkit.SslErrorHandler
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.core.extensions.toSecureUrl
import com.joaoneres.uolchallenge.ui.componentes.UolTopBar
import com.joaoneres.uolchallenge.ui.theme.Dimens
import kotlinx.coroutines.delay

@Composable
fun WebViewScreen(
    navController: NavController,
    url: String
) {

    var hasError by remember(url) {
        mutableStateOf(url.isBlank())
    }

    var isLoading by remember(url) {
        mutableStateOf(url.isNotBlank())
    }

    var reloadKey by remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(url, reloadKey, isLoading) {
        if (url.isNotBlank() && isLoading) {
            delay(15_000)

            if (isLoading) {
                hasError = true
                isLoading = false
            }
        }
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
                    message = stringResource(
                        R.string.web_view_screen_error_message
                    ),
                    onRetry = {
                        hasError = false
                        isLoading = true
                        reloadKey++
                    }
                )
            }

        } else {

            key(reloadKey) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {

                    AndroidView(
                        modifier = Modifier.fillMaxSize(),
                        factory = { context ->

                            WebView(context).apply {

                                webViewClient = object : WebViewClient() {

                                    override fun onPageStarted(
                                        view: WebView?,
                                        url: String?,
                                        favicon: Bitmap?
                                    ) {
                                        super.onPageStarted(view, url, favicon)
                                        isLoading = true
                                    }

                                    override fun onPageFinished(
                                        view: WebView?,
                                        url: String?
                                    ) {
                                        super.onPageFinished(view, url)

                                        if (
                                            url.isNullOrBlank() ||
                                            url.startsWith("chrome-error://")
                                        ) {
                                            hasError = true
                                        }

                                        isLoading = false
                                    }

                                    override fun onReceivedError(
                                        view: WebView?,
                                        request: WebResourceRequest?,
                                        error: WebResourceError?
                                    ) {
                                        super.onReceivedError(
                                            view,
                                            request,
                                            error
                                        )

                                        if (request?.isForMainFrame == true) {
                                            isLoading = false
                                            hasError = true
                                        }
                                    }

                                    override fun onReceivedHttpError(
                                        view: WebView?,
                                        request: WebResourceRequest?,
                                        errorResponse: WebResourceResponse?
                                    ) {
                                        super.onReceivedHttpError(
                                            view,
                                            request,
                                            errorResponse
                                        )

                                        if (
                                            request?.isForMainFrame == true &&
                                            errorResponse?.statusCode in 400..599
                                        ) {
                                            isLoading = false
                                            hasError = true
                                        }
                                    }

                                    override fun onReceivedSslError(
                                        view: WebView?,
                                        handler: SslErrorHandler?,
                                        error: SslError?
                                    ) {
                                        handler?.cancel()

                                        isLoading = false
                                        hasError = true
                                    }
                                }

                                settings.javaScriptEnabled = true
                                settings.domStorageEnabled = true
                                settings.javaScriptCanOpenWindowsAutomatically = true

                                CookieManager
                                    .getInstance()
                                    .setAcceptCookie(true)

                                CookieManager
                                    .getInstance()
                                    .setAcceptThirdPartyCookies(this, true)

                                loadUrl(url.toSecureUrl())
                            }
                        }
                    )

                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(Dimens.spacing80)
                                .align(Alignment.Center),
                            strokeWidth = Dimens.spacing8
                        )
                    }
                }
            }
        }
    }
}
