package com.example.banquemisrchallenge05movieapp.utils.shared_components

import android.annotation.SuppressLint
import android.graphics.fonts.FontStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier // Correct import for Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.banquemisrchallenge05movieapp.R

@Composable
@SuppressLint("NewApi")
fun MovieAppText(
    text: String,
    style: TextStyle = TextStyle(),
    fountStyle: FontStyle = FontStyle(),
    fontSize: TextUnit = 16.sp,
    color: Color = Color.White,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight:  TextUnit = 24.sp,
) {
    Text(
        text = text,
        fontFamily = FontFamily(
            Font(R.font.normativeprobold)
        ),
        fontSize = fontSize,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        lineHeight = lineHeight,


    )
}
