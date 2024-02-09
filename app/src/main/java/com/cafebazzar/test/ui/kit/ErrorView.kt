package com.cafebazzar.test.ui.kit

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cafebazzar.test.R
@Preview
@Composable
fun ErrorView(
    @DrawableRes imageResId: Int = R.drawable.ic_launcher_foreground,
    errorMessage: String = "Ops...EROR",
    imageSize: Dp = 220.dp,
    messageFontSize: TextUnit = 14.sp,
    ButtonFontSize: TextUnit = 14.sp,
    onRetryClick: (() -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(imageResId),
            contentDescription = "ErrorImageView",
            modifier = Modifier.size(imageSize)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = errorMessage,
            modifier = Modifier.fillMaxWidth(),
            fontSize = messageFontSize,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(8.dp))
        if (onRetryClick != null) {
            Button(
                onClick = {
                    onRetryClick()
                },
            ) {
                Text(
                    text = errorMessage,
                    color = Color.White,
                    fontSize = ButtonFontSize
                )
            }
        }
    }
}
