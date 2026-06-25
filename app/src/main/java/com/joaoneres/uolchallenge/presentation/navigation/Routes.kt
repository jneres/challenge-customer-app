package com.joaoneres.uolchallenge.presentation.navigation

import android.net.Uri

object Routes {

    const val CUSTOMER_LIST = "customer_list"
    const val IMAGE = "image/{imageUrl}"
    const val WEBVIEW = "webview/{url}"

    fun imageRoute(imageUrl: String) =
        "image/${Uri.encode(imageUrl)}"

    fun webViewRoute(url: String) =
        "webview/${Uri.encode(url)}"
}