package com.joaoneres.uolchallenge.presentation.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.core.extensions.toRawGithubImageUrl
import com.joaoneres.uolchallenge.presentation.componentes.UolTopBar
import com.joaoneres.uolchallenge.ui.theme.UolChallengeTheme

@Composable
fun ImageScreen(
    navController: NavController,
    imageUrl: String
) {

    Scaffold(
        topBar = {
            UolTopBar(
                title = stringResource(R.string.image_screen_title),
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                model = imageUrl.toRawGithubImageUrl(),
                contentDescription = stringResource(R.string.image_screen_title),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ImageScreenPreview() {
    UolChallengeTheme {
        ImageScreen(
            navController = rememberNavController(),
            imageUrl = "https://raw.githubusercontent.com/newloran2/testApp2026/main/imagens/macaco1.jpg"
        )
    }
}