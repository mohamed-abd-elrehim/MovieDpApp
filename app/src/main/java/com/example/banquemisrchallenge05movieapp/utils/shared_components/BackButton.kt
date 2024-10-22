package com.example.banquemisrchallenge05movieapp.utils.shared_components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.banquemisrchallenge05movieapp.R

@Composable
fun BackButton(onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = stringResource(R.string.back),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(30.dp)
                .clickable { onBackClick() }
                .align(Alignment.Center)
        )

    }
}