package com.example.banquemisrchallenge05movieapp.detailscreen.view

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.banquemisrchallenge05movieapp.R
import com.example.banquemisrchallenge05movieapp.detailscreen.viewModel.DetailViewModel
import com.example.banquemisrchallenge05movieapp.utils.shared_components.ApiCallState
import com.example.banquemisrchallenge05movieapp.ui.theme.Gold
import com.example.banquemisrchallenge05movieapp.ui.theme.PrimaryColor
import com.example.banquemisrchallenge05movieapp.utils.constants.APIKeys
import com.example.banquemisrchallenge05movieapp.utils.shared_components.BackButton
import com.example.banquemisrchallenge05movieapp.utils.shared_components.ElevationCard
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_components.MovieAppText
import com.example.banquemisrchallenge05movieapp.utils.shared_components.displayNoInternetMessage
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.LoadAsyncImage
import com.example.banquemisrchallenge05movieapp.utils.shared_models.MovieDetails
import com.example.banquemisrchallenge05movieapp.utils.shared_methods.isNetworkAvailable

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(navController: NavHostController, viewModel: DetailViewModel, movieId: String) {
    val context = LocalContext.current

    viewModel.getMovieDetails(movieId.toInt())

    val isInternetAvailable = isNetworkAvailable(context)
    val localMovieDetails by viewModel.localMovieDetails.collectAsStateWithLifecycle()
    val movieDetails by viewModel.movieDetails.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()


    var movie by remember { mutableStateOf<MovieDetails?>(null) }
    movie = localMovieDetails

    LaunchedEffect(movieId) {
        if (isInternetAvailable) {
            Log.d("DetailScreen", "LaunchedEffect: $movieId")
            viewModel.featchMovieDetails(movieId.toInt())
        }
    }

    if (isInternetAvailable) {
        ApiCallState(
            navController = navController,
            apiState = movieDetails,
            onSuccess = {
                movie = it
                viewModel.insertMovieDetails(it)
            }
        )
    }



    if (movie != null) {
        Column() {
            ElevationCard(
            ) {
                Box(
                ) {
                    LoadAsyncImage(
                        context = context,
                        imageUrl = APIKeys.MOVIEDB_IMAGE_URL + movie?.backdrop_path,
                        movie?.title ?: " No Title ",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(400.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.FillBounds

                    )

                    BackButton(
                        onBackClick = {
                            navController.popBackStack()

                        }, modifier = Modifier
                            .padding(16.dp)
                            .size(50.dp)
                            .background(
                                PrimaryColor.copy(alpha = 0.7f), RoundedCornerShape(15.dp)
                            )
                    )

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .clip(RoundedCornerShape(10.dp))
                            .background(PrimaryColor.copy(alpha = 0.7f))
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically


                    ) {
                        if (movie?.vote_average ?: 0.0 > 0.0) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Star Icon",
                                modifier = Modifier.size(30.dp),
                                tint = Gold
                            )
                            MovieAppText(
                                text = "${
                                    String.format(
                                        "%.2f", movie?.vote_average
                                    )
                                }",
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                            )
                            Gap(width = 10)
                            VerticalDivider(
                                modifier = Modifier
                                    .height(30.dp)
                                    .width(3.dp),
                                color = Color.White
                            )
                        }

                        Gap(width = 10)

                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Date Icon",
                            modifier = Modifier.size(25.dp),
                            tint = Gold
                        )
                        MovieAppText(
                            text = "${movie?.release_date}",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(top = 5.dp, start = 5.dp)
                        )
                        Gap(width = 10)
                        VerticalDivider(
                            modifier = Modifier
                                .height(30.dp)
                                .width(3.dp),
                            color = Color.White
                        )
                        Gap(width = 10)
                        var adult = "+12"
                        if (movie?.adult == true) {
                            adult = "+18"
                        }

                        MovieAppText(
                            text = adult,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 5.dp),
                            color = Color.White
                        )
                    }


                }
            }
            Gap(height = 16)
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {

                MovieAppText(
                    text = movie?.title ?: " No Title ",
                    fontSize = 30.sp,
                    color = PrimaryColor,
                    modifier = Modifier.padding(start = 16.dp),
                    textAlign = TextAlign.Start,
                    lineHeight = 30.sp
                )

                Gap(height = 8)

                Text(
                    text = movie?.overview ?: stringResource(R.string.no_overview),
                    fontSize = 17.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .padding(start = 20.dp, end = 16.dp),
                    lineHeight = 25.sp,
                    color = PrimaryColor.copy(alpha = 0.7f)
                )

                Gap(height = 20)
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Genres text aligned to the start
                    MovieAppText(
                        text = movie?.genres?.joinToString(" . ") { it.name }
                            ?: "Unknown Genre",
                        fontSize = 16.sp,
                        color = PrimaryColor,

                        )

                    Spacer(modifier = Modifier.weight(1f))

                    MovieAppText(
                        text = viewModel.getFormattedRuntime(movie?.runtime ?: 0),
                        fontSize = 16.sp,
                        color = PrimaryColor,
                    )
                }

                Gap(height = 16)
                if (movie?.budget ?: 0 > 0) {

                    MovieAppText(
                        text = "Budget: \$${movie?.budget ?: 0 / 1_000_000}M",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp),
                        color = PrimaryColor
                    )
                }
                if (movie?.revenue ?: 0 > 0) {
                    Gap(height = 16)
                    MovieAppText(
                        text = "Revenue: \$${movie?.revenue ?: 0 / 1_000_000}M",
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 16.dp),
                        color = PrimaryColor
                    )
                }

                Gap(height = 16)
                MovieAppText(
                    text = "${stringResource(R.string.production_companies)}:",
                    fontSize = 16.sp,
                    color = PrimaryColor,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Gap(height = 8)
                Text(
                    text = "${movie?.production_companies?.joinToString(", ") { it.name } ?: "Unknown Production Companies"}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 25.dp, end = 16.dp ,bottom = 25.dp),
                    lineHeight = 25.sp,
                    color = PrimaryColor
                )

            }

        }} else {
            displayNoInternetMessage()
        }

}
