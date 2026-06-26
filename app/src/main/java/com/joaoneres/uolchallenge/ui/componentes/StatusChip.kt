package com.joaoneres.uolchallenge.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.ui.theme.ActiveBackground
import com.joaoneres.uolchallenge.ui.theme.ActiveDot
import com.joaoneres.uolchallenge.ui.theme.Dimens
import com.joaoneres.uolchallenge.ui.theme.InactiveBackground
import com.joaoneres.uolchallenge.ui.theme.InactiveDot

private const val ACTIVE_STATUS = "active"

@Composable
fun StatusChip(
    status: String
) {

    val isActive = status.equals(
        ACTIVE_STATUS,
        ignoreCase = true
    )

    val backgroundColor = if (isActive) {
        ActiveBackground
    } else {
        InactiveBackground
    }

    val dotColor = if (isActive) {
        ActiveDot
    } else {
        InactiveDot
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
                horizontal = Dimens.spacing10,
                vertical = Dimens.spacing4
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(Dimens.spacing10)
                .background(
                    dotColor,
                    CircleShape
                )
        )

        Spacer(
            modifier = Modifier.width(Dimens.spacing8)
        )

        Text(
            text = text,
            color = dotColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}