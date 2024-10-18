package com.example.banquemisrchallenge05movieapp.detailscreen.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import coil.compose.rememberAsyncImagePainter
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import com.example.banquemisrchallenge05movieapp.utils.shared_components.SharedHeader

data class Movie(
    val title: String,
    val backdropPath: String,
    val posterPath: String,
    val genres: List<String>,
    val releaseDate: String,
    val rating: Double,
    val overview: String,
    val productionCompanies: List<String>,
    val budget: Int,
    val revenue: Int,
    val runtime: Int,
    val tagline: String
)

@Composable
fun DetailScreen(navController: NavController) {
    val movie = Movie(
        title = "The Wild Robot",
        backdropPath = "https://image.tmdb.org/t/p/w500/417tYZ4XUyJrtyZXj7HpvWf1E8f.jpg",
        posterPath = "https://image.tmdb.org/t/p/w500/9w0Vh9eizfBXrcomiaFWTIPdboo.jpg",
        genres = listOf("Animation", "Science Fiction", "Family"),
        releaseDate = "2024-09-12",
        rating = 8.6,
        overview = "After a shipwreck, an intelligent robot called Roz is stranded on an uninhabited island. To survive the harsh environment, Roz bonds with the island's animals and cares for an orphaned baby goose.",
        productionCompanies = listOf("DreamWorks Animation"),
        budget = 78000000,
        revenue = 152822000,
        runtime = 102,
        tagline = "Discover your true nature."
    )

    Column (
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        SharedHeader(navController, "Movie Details")
        Gap(16)
        Column(
            modifier = Modifier
                .padding(16.dp,16.dp,16.dp,25.dp)

        ) {

            Image(
                painter = rememberAsyncImagePainter(movie.backdropPath),
                contentDescription = movie.title,
                contentScale = ContentScale.Crop,
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

            Text(
                text = movie.tagline,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Gap(height = 16)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = rememberAsyncImagePainter(movie.posterPath),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Gap(width = 16)

                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(text = "Release Date: ${movie.releaseDate}", fontSize = 16.sp)
                    Text(text = "Rating: ${movie.rating}/10", fontSize = 16.sp)
                    Text(text = "Runtime: ${movie.runtime} min", fontSize = 16.sp)
                    Text(text = "Budget: \$${movie.budget / 1_000_000}M", fontSize = 16.sp)
                    Text(text = "Revenue: \$${movie.revenue / 1_000_000}M", fontSize = 16.sp)
                }
            }

            Gap(height = 16)

            Text(
                text = "Genres: ${movie.genres.joinToString(", ")}",
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
                text = movie.productionCompanies.joinToString(", "),
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(navController = NavController(LocalContext.current))

}