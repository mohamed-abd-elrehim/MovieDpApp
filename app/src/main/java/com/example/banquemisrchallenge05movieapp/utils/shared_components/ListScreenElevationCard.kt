package com.example.banquemisrchallenge05movieapp.utils.shared_components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.LoadAsyncImage

@Composable
fun ListScreenElevationCard(
    modifier: Modifier = Modifier,
    context: Context,
    posterImage: String = "https://images.app.goo.gl/BkrQKJVnfaXvnWRk6",
    title: String = "The Lord of the Rings",
    releaseDate: String = "1/1/1994",
    rating: String = "9.3"
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            LoadAsyncImage(
                context = context,
                imageUrl = posterImage,
                "poster",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp, 200.dp)
                    .background(Color.Black)
                    .padding(8.dp)
            )
            Gap(height = 8)
            Text(
                text = title,
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            Gap(height = 4)
            Text(
                text = "Release Date: $releaseDate",
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium
            )
            Gap(height = 4)
            Row {
                Text(
                    text = "Rating: $rating",
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun ListScreenElevationCardPreview() {
    ListScreenElevationCard(context = LocalContext.current)
}
