package com.joaoneres.uolchallenge.presentation.customerlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.core.extensions.toRawGithubImageUrl
import com.joaoneres.uolchallenge.domain.model.Customer
import com.joaoneres.uolchallenge.ui.componentes.StatusChip
import com.joaoneres.uolchallenge.ui.theme.Dimens
import com.joaoneres.uolchallenge.ui.theme.UolChallengeTheme

@Composable
fun CustomerCard(
    customer: Customer,
    onImageClick: (String) -> Unit = {},
    onProfileClick: (String) -> Unit = {}
) {

    Card(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .padding(vertical = Dimens.spacing6, horizontal = Dimens.spacing12),
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimens.spacing4
        )
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimens.spacing12)
        ) {

            customer.status?.let { StatusChip(it) }

            Spacer(modifier = Modifier.height(Dimens.spacing12))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                val imageUrl = customer.profileImage?.toRawGithubImageUrl()

                Box(
                    modifier = Modifier
                        .size(Dimens.spacing80)
                        .clip(CircleShape)
                        .border(
                            width = Dimens.borderLight,
                            color = Color.LightGray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = customer.name,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(R.drawable.ic_person_placeholder),
                        error = painterResource(R.drawable.ic_person_placeholder),
                        fallback = painterResource(R.drawable.ic_person_placeholder),
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(Dimens.spacing16))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = customer.name ?: stringResource(R.string.customer_list_card_no_name),
                        style = MaterialTheme.typography.titleMedium,
                    )

                    if (!customer.email.isNullOrBlank()) {
                        Row(
                            modifier = Modifier.padding(top = Dimens.spacing6),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(Dimens.spacing16)
                            )
                            Spacer(modifier = Modifier.width(Dimens.spacing8))
                            Text(text = customer.email)
                        }
                    }

                    if (!customer.phone.isNullOrBlank()) {

                        Row(
                            modifier = Modifier.padding(top = Dimens.spacing6),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Phone,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(Dimens.spacing16)
                            )
                            Spacer(modifier = Modifier.width(Dimens.spacing8))
                            Text(text = customer.phone)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(Dimens.spacing12))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                OutlinedButton(
                    onClick = {
                        onImageClick(customer.profileImage.orEmpty())
                    }
                ) {
                    Text(stringResource(R.string.customer_list_card_image_button))
                }

                Spacer(modifier = Modifier.width(Dimens.spacing8))

                Button(
                    onClick = {
                        onProfileClick(customer.profileLink.orEmpty())
                    }
                ) {
                    Text(stringResource(R.string.customer_list_card_profile_button))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomerCardWithoutImagePreview() {

    UolChallengeTheme {

        CustomerCard(
            customer = Customer(
                id = "2",
                name = "José Silva",
                email = "jose@email.com",
                phone = null,
                profileImage = null,
                profileLink = null,
                status = "active"
            )
        )

    }
}