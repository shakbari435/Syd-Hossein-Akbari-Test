package com.cafebazzar.test.presentation.ui.kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cafebazzar.test.R
import com.cafebazzar.test.common.ui.theme.LoadingColor

@Preview
@Composable
fun LoadingView(isPlaceHolder: Boolean = false) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isPlaceHolder) {
            Image(
                modifier = Modifier.padding(bottom = 50.dp),
                painter = painterResource(
                    id = R.drawable.bazaar_logo_2_
                ),
                contentDescription = ""
            )
        }
        CircularProgressIndicator(
            color = LoadingColor
        )
    }
}