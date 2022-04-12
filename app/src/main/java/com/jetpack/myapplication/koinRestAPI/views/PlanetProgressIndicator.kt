package com.jetpack.myapplication.koinRestAPI.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun PlanetProgressIndicator(isDisplayed: Boolean) {

    if (isDisplayed) {
        Dialog(
            onDismissRequest = { isDisplayed },
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(MaterialTheme.colors.background, shape = RoundedCornerShape(12.dp))
            ) {
                Column {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(6.dp),
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = "Loading...",
                        Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp),
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}
