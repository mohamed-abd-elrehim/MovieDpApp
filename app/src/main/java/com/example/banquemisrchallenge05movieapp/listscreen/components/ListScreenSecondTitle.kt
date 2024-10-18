package com.example.banquemisrchallenge05movieapp.listscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ListScreenSecondTitle(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth() // Make the Box take full width
            .padding(16.dp) // Padding around the Box
            .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp)) // Optional border
            .background(Color.LightGray.copy(alpha = 0.5f)) // Light gray background
            .padding(16.dp), // Inner padding for the text
        contentAlignment = Alignment.Center // Center the content within the Box
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium, // Updated to use Material3 typography
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
    }
}
