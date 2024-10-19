package com.example.banquemisrchallenge05movieapp.detailscreen.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_components.BackButton
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_components.HeaderText
import com.example.banquemisrchallenge05movieapp.utils.shared_components.LottieWithText
import com.example.banquemisrchallenge05movieapp.utils.shared_components.MovieAppLoadingIndicator
import com.example.banquemisrchallenge05movieapp.utils.shared_components.SharedHeader
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.LoadAsyncImage
import com.example.banquemisrchallenge05movieapp.utils.shared_models.ApiState
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.shared_models.isNetworkAvailable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(navController: NavHostController, viewModel: DetailViewModel, movieId: Int) {
    val context = LocalContext.current

    viewModel.getMovieDetails(movieId)

    val localMovieDetails by viewModel.localMovieDetails.collectAsStateWithLifecycle()

    var movie by remember { mutableStateOf<MovieDetails?>(null) }

    if (localMovieDetails != null) {
        movie = localMovieDetails
    }

    LaunchedEffect(localMovieDetails == null) {
        viewModel.featchMovieDetails(movieId)
    }

    val movieDetails by viewModel.movieDetails.collectAsStateWithLifecycle()
    val scrollState = rememberLazyListState()

    if (movie == null && isNetworkAvailable(context)) {
        when (movieDetails) {
            ApiState.Loading -> {
                MovieAppLoadingIndicator()
            }

            is ApiState.Success -> {
                val fetchedMovie = (movieDetails as ApiState.Success<MovieDetails>).data
                movie = fetchedMovie
                movie?.let { viewModel.insertMovieDetails(it) }

            }

            is ApiState.Error -> {
                Toast.makeText(
                    context,
                    (movieDetails as ApiState.Error).message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    if (movie != null) {

// Scrollable content with Sticky Header
        LazyColumn(
            state = scrollState, modifier = Modifier.fillMaxSize()
        ) {

            item {
                Text(
                    text = stringResource(R.string.app_name),
                    fontSize = 24.sp,
                    color = PrimaryColor,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            // Sticky Header for Back Button
            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Back Button
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        BackButton(onBackClick = {
                            navController.popBackStack()
                        })
                        Gap(width = 20)
                        HeaderText(headerText = stringResource(R.string.movie_details))
                    }
                }
            }

            // Scrollable Movie Details content
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize()
                ) {
                    LoadAsyncImage(
                        context = context,

                        imageUrl = APIKeys.MOVIEDB_IMAGE_URL + movie?.backdrop_path,
                        movie?.title ?: " No Title ",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Gap(height = 16)

                    Text(
                        text = movie?.title ?: " No Title ",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )

                    Gap(height = 8)

                    movie?.tagline?.let {
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
                            imageUrl = APIKeys.MOVIEDB_IMAGE_URL + movie?.poster_path,
                            movie?.title ?: " No Title ",
                            modifier = Modifier
                                .width(150.dp)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.None
                        )

                        Gap(width = 16)

                        Column(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) {
                            Text(text = "Release Date: ${movie?.release_date}", fontSize = 16.sp)
                            if (movie?.vote_average ?: 0.0 > 0.0) {
                                Text(
                                    text = "Rating: ${
                                        String.format(
                                            "%.2f", movie?.vote_average
                                        )
                                    }/10", fontSize = 16.sp
                                )
                            }
                            Text(text = "Runtime: ${movie?.runtime} min", fontSize = 16.sp)
                            if (movie?.budget ?: 0 > 0) {
                                Text(
                                    text = "Budget: \$${movie?.budget ?: 0 / 1_000_000}M",
                                    fontSize = 16.sp
                                )
                            }
                            if (movie?.revenue ?: 0 > 0) {

                                Text(
                                    text = "Revenue: \$${movie?.revenue ?: 0 / 1_000_000}M",
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                    Gap(height = 16)

                    Text(
                        text = "Genres: ${movie?.genres?.map { it.name }?.joinToString(", ")}",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )

                    Gap(height = 16)

                    Text(
                        text = stringResource(R.string.overview),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = movie?.overview ?: stringResource(R.string.no_overview),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Gap(height = 16)

                    Text(
                        text = stringResource(R.string.production_companies),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    movie?.production_companies?.map { it.name }?.let {
                        Text(
                            text = it.joinToString(", "),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Left,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }else {
        Column {
            SharedHeader(
                navController = navController,
                headerText = stringResource(R.string.movie_details)
            )
            Gap(height = 16)
            LottieWithText(R.raw.no_internet, stringResource(R.string.no_internet_connection))
        }
    }
}