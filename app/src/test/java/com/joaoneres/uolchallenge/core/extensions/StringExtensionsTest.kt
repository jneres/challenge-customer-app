package com.joaoneres.uolchallenge.core.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test

class StringExtensionsTest {

    @Test
    fun `should convert github image url to raw github url`() {
        val githubUrl =
            "https://github.com/newloran2/testApp/blob/main/image.png"

        val expected =
            "https://raw.githubusercontent.com/newloran2/testApp2026/main/image.png"

        val result = githubUrl.toRawGithubImageUrl()

        assertEquals(expected, result)
    }
}