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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.constants.NavigationKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.LoadAsyncImage
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieResult

@Composable
fun ListScreenElevationCard(
    modifier: Modifier = Modifier,
    context: Context,
    movieResult: MovieResult? = null,
    navController: NavController
) {
    Card(
        modifier = modifier
            .padding(10.dp)
            .size(180.dp, 150.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        onClick = {
            movieResult?.let {
                navController.navigate("${NavigationKeys.DetailScreen}/${it.id}")
            }
        }
    ) {

        Column {
            if (movieResult != null) {
                Box() {
                    // Load Image
                    movieResult.poster_path?.let {
                        LoadAsyncImage(
                            context = context,
                            imageUrl = APIKeys.MOVIEDB_IMAGE_URL + it,
                            movieResult.title,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (movieResult.vote_average > 0.0) {
                        Text(
                            text = "${String.format("%.2f", movieResult.vote_average)}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .background(
                                    PrimaryColor.copy(alpha = 0.7f),
                                    RoundedCornerShape(10.dp)
                                )
                                .padding(4.dp)

                        )
                    }
                    Column(
                        modifier = Modifier

                            .align(Alignment.BottomStart)
                            .background(PrimaryColor.copy(alpha = 0.7f), RoundedCornerShape(12.dp))
                            .padding(6.dp)
                            .width(170.dp)

                    ) {
                        // Movie Title
                        Text(
                            text = movieResult.title,
                            color = Color.White,
                            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = "Release Date: ${movieResult.release_date}",
                            color = Color.White.copy(alpha = 0.8f),
                            style = MaterialTheme.typography.bodyMedium
                        )


                    }
                }
            }
        }

    }
}

