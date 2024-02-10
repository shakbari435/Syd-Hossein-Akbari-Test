package com.cafebazzar.test.ui.kit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cafebazzar.test.R

@Preview
@Composable
fun ErrorView(
    @DrawableRes imageResId: Int = R.drawable.ic_error,
    errorMessage: String = "Connection glitch",
    errorDescription: String = "Seems like there's an internet\n" +
            "connection problem.",
    onRetryClick: (() -> Unit)? = null,
) {
    val isDarkTheme = isSystemInDarkTheme()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = "ErrorImageView",
            modifier = Modifier.wrapContentSize()
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            color = if (isDarkTheme) {
                Color.White
            } else {
                Color.Black
            },
            text = errorMessage,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            color = Color(0xFF7E91B7),
            text = errorDescription,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(25.dp))
        if (onRetryClick != null) {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C3051)),
                onClick = {
                    onRetryClick()
                },
            ) {
                Text(
                    text = "Retry",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }
    }
}
