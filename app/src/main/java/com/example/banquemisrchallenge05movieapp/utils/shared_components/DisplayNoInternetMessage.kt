package com.example.banquemisrchallenge05movieapp.utils.shared_components

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.banquemisrchallenge05movieapp.R

@Composable
fun displayNoInternetMessage() {
    Gap(height = 16)
    LottieWithText(
        R.raw.no_internet,
        stringResource(R.string.no_internet_connection)
    )
}
