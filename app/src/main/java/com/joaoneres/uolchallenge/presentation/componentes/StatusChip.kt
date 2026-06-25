package com.joaoneres.uolchallenge.presentation.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.joaoneres.uolchallenge.R

@Composable
fun StatusChip(
    status: String
) {

    val isActive = status.equals(
        "active",
        ignoreCase = true
    )

    val backgroundColor = if (isActive) {
        Color(0xFFE8F8EC)
    } else {
        Color(0xFFFFEBEE)
    }

    val dotColor = if (isActive) {
        Color(0xFF22C55E)
    } else {
        Color(0xFFE53935)
    }

    val text = if (isActive) {
        stringResource(R.string.status_active)
    } else {
        stringResource(R.string.status_inactive)
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20))
            .background(backgroundColor)
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(10.dp)
                .background(
                    dotColor,
                    CircleShape
                )
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Text(
            text = text,
            color = dotColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}