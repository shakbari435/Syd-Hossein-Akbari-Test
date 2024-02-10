package com.cafebazzar.test.presentation.ui.kit

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Preview
@Composable
fun ItemView(imageUrl: String, title: String) {
    val url = "https://image.tmdb.org/t/p/w500$imageUrl"
    val isDarkTheme = isSystemInDarkTheme()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.wrapContentSize()
    ) {
        AsyncImage(
            ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .padding(bottom = 19.dp)
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(9f / 12f),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = if (isDarkTheme) {
                Color.White
            } else {
                Color.Black
            },
            text = title
        )
    }
}