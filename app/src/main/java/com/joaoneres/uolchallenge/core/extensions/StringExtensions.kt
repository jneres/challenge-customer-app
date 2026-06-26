package com.joaoneres.uolchallenge.core.extensions

fun String.toRawGithubImageUrl(): String {
    return this
        .replace("https://github.com/", "https://raw.githubusercontent.com/")
        .replace("/blob/", "/")
        .replace("?raw=true", "")
        .replace(
            "newloran2/testApp/",
            "newloran2/testApp2026/"
        )
}

fun String.toSecureUrl(): String {
    return when {
        startsWith("https://") -> this
        startsWith("http://") -> replaceFirst("http://", "https://")
        else -> "https://$this"
    }
}