package com.example.banquemisrchallenge05movieapp.listscreen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.banquemisrchallenge05movieapp.utils.shared_components.Gap
import androidx.compose.material3.IconButton
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.example.banquemisrchallenge05movieapp.R

@Composable
fun Pagination(
    currentPage: Int=1, totalPages: Int, onPageChange: (Int) -> Unit
) {
    Gap(height = 10)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Page: $currentPage / $totalPages", color = Color.Black)

        Row {
            IconButton(
                onClick = { if (currentPage > 1) onPageChange(currentPage - 1) },
                enabled = currentPage > 1
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_previous_arrow),
                    contentDescription = stringResource(R.string.previous_page)
                )
            }
            OutlinedTextField(
                value = currentPage.toString(), onValueChange = { input ->
                    val newPage = input.toIntOrNull()
                    if (newPage != null && newPage in 1..totalPages) {
                        onPageChange(newPage)
                    }
                }, modifier = Modifier.size(50.dp), textStyle = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            )
            IconButton(
                onClick = { if (currentPage < totalPages) onPageChange(currentPage + 1) },
                enabled = currentPage < totalPages
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_next_arrow),
                    contentDescription = stringResource(R.string.next_page)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPagination() {
    Pagination(currentPage = 1, totalPages = 10, onPageChange = {})
}
