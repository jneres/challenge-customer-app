package com.joaoneres.uolchallenge.presentation.navigation

import android.net.Uri

object Routes {
    private const val PATH_IMAGE = "image"
    private const val PATH_WEBVIEW = "webview"
    const val CUSTOMER_LIST = "customer_list"
    const val IMAGE = "image/{imageUrl}"
    const val WEBVIEW = "webview/{url}"

    const val URL = "url"
    const val IMAGE_URL = "imageUrl"

    fun imageRoute(imageUrl: String) =
        "$PATH_IMAGE/${Uri.encode(imageUrl)}"

    fun webViewRoute(url: String) =
        "$PATH_WEBVIEW/${Uri.encode(url)}"
}