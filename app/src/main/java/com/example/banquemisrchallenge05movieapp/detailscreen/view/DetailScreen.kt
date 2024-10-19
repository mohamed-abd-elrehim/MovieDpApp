package com.example.banquemisrchallenge05movieapp.detailscreen.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_components.MovieAppLoadingIndicator
import com.example.banquemisrchallenge05movieapp.utils.shared_components.SharedHeader
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.LoadAsyncImage
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails


@Composable
fun DetailScreen(navController: NavHostController, viewModel: DetailViewModel, movieId: Int) {
    val context = LocalContext.current

    LaunchedEffect(movieId) {
        viewModel.featchMovieDetails(movieId)
    }

    val movieDetails by viewModel.movieDetails.collectAsStateWithLifecycle()

    when (movieDetails) {
        ApiState.Loading -> {
            MovieAppLoadingIndicator()
        }

        is ApiState.Success -> {
            val movie = (movieDetails as ApiState.Success<MovieDetails>).data

            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                SharedHeader(navController, "Movie Details")
                Gap(16)
                Column(
                    modifier = Modifier
                        .padding(16.dp, 16.dp, 16.dp, 25.dp)
                ) {

                    LoadAsyncImage(
                        context = context,
                        imageUrl = APIKeys.MOVIEDB_IMAGE_URL + movie.backdrop_path,
                        movie.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )


                    Gap(height = 16)

                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Gap(height = 8)

                    movie.tagline?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            fontStyle = FontStyle.Italic,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Gap(height = 16)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start
                    ) {

                        LoadAsyncImage(
                            context = context,
                            imageUrl = APIKeys.MOVIEDB_IMAGE_URL + movie.poster_path,
                            movie.title,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )

                        Gap(width = 16)

                        Column(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(text = "Release Date: ${movie.release_date}", fontSize = 16.sp)
                            Text(text = "Rating: ${String.format("%.2f", movie.vote_average)}/10", fontSize = 16.sp)
                            Text(text = "Runtime: ${movie.runtime} min", fontSize = 16.sp)
                            Text(text = "Budget: \$${movie.budget / 1_000_000}M", fontSize = 16.sp)
                            Text(text = "Revenue: \$${movie.revenue / 1_000_000}M", fontSize = 16.sp)
                        }
                    }

                    Gap(height = 16)

                    Text(
                        text = "Genres: ${movie.genres.map { it.name }.joinToString(", ")}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Gap(height = 16)

                    Text(
                        text = "Overview:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = movie.overview,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Gap(height = 16)

                    Text(
                        text = "Production Companies:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(

                        text = movie.production_companies.map { it.name }.joinToString(", "),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        is ApiState.Error -> {
            Toast.makeText(context, (movieDetails as ApiState.Error).message, Toast.LENGTH_SHORT).show()
        }
    }
}
