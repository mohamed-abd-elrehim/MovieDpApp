package com.example.banquemisrchallenge05movieapp.listscreen.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.LoadAsyncImage
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieResult
import com.example.e_store.utils.shared_components.MovieAppButton

@Composable
fun ListScreenElevationCard(
    modifier: Modifier = Modifier,
    context: Context,
    movieResult: MovieResult? = null,
) {
    Card(
        modifier = modifier
            .width(200.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {

        Column {
            if (movieResult != null) {
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                ) {
                    // Load Image
                    movieResult.poster_path?.let {
                        LoadAsyncImage(
                            context = context,
                            imageUrl = APIKeys.MOVIEDB_IMAGE_URL + it,
                            "poster",
                            modifier = Modifier
                                .fillMaxSize() // Fill the box
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }

                    // Rating Text on top of the image
                    Text(
                        text = "${String.format("%.2f", movieResult.vote_average)}", // Format to two decimal places
                        color = Color.White, // Change text color to white for better contrast
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .align(Alignment.TopStart) // Aligns the text to the top left corner
                            .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp)) // Optional background for better visibility
                            .padding(4.dp) // Padding around the text
                    )
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                if (movieResult != null) {
                    Text(
                        text = movieResult.title,
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (movieResult != null) {
                    Text(
                        text = "Release Date: ${movieResult.release_date}",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Gap(height = 10)

                MovieAppButton(text = stringResource(R.string.see_details), onClick = {

                })
            }
        }

    }
}

@Preview
@Composable
fun ListScreenElevationCardPreview() {
    ListScreenElevationCard(context = LocalContext.current)
}
