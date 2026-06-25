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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.joaoneres.uolchallenge.R
import com.joaoneres.uolchallenge.core.extensions.toRawGithubImageUrl
import com.joaoneres.uolchallenge.data.model.Customer
import com.joaoneres.uolchallenge.presentation.componentes.StatusChip
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
            .padding(vertical = 6.dp, horizontal = 12.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp)
        ) {

            customer.status?.let { StatusChip(it) }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                val imageUrl = customer.profileImage?.toRawGithubImageUrl()

                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {

                    if (imageUrl != null) {

                        AsyncImage(
                            model = imageUrl,
                            contentDescription = customer.name,
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(R.drawable.ic_person_placeholder),
                            error = painterResource(R.drawable.ic_person_placeholder),
                            modifier = Modifier.fillMaxSize()
                        )

                    } else {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(72.dp)
                        )

                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = customer.name ?: stringResource(R.string.customer_list_card_no_name),
                        style = MaterialTheme.typography.titleMedium,
                        )

                    if (!customer.email.isNullOrBlank()) {
                        Row(
                            modifier = Modifier.padding(top = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Email,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = customer.email)
                        }
                    }

                    if (!customer.phone.isNullOrBlank()) {

                        Row(
                            modifier = Modifier.padding(top = 6.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Phone,
                                contentDescription = null,
                                tint = Color.Gray,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = customer.phone)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                OutlinedButton(
                    onClick = {
                        customer.profileImage?.let(onImageClick)
                    }
                ) {
                    Text(stringResource(R.string.customer_list_card_image_button))
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        customer.profileLink?.let(onProfileClick)
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