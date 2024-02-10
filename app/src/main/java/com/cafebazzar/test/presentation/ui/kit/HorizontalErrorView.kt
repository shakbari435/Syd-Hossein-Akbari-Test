package com.cafebazzar.test.presentation.ui.kit

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HorizontalErrorView(
    errorMessage: String = "Something went wrong",
    isDarkTheme: Boolean = false,
    onRetryClick: (() -> Unit)? = null,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            color = if (isDarkTheme) {
                Color.White
            } else {
                Color.Black
            },
            text = errorMessage,
            modifier = Modifier.wrapContentSize(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Button(
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(
                width = 1.dp, color = Color(0xFF44464E)
            ),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C3051)),
            onClick = {
                 onRetryClick?.invoke()
            },
        ) {
            Text(
                text = "Retry",
                color = Color.Red,
                fontSize = 14.sp
            )
        }
    }
}

