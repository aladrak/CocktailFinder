package com.example.cocktail_finder.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Line(
    height: Dp
) {
    Box( // Line
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .background(Color(0, 0, 0))
    )
}
@Composable
fun LargeText (
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        fontSize = 25.sp,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun MediumText (
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center
) {
    Text (
        text = text,
        modifier = modifier,
        fontSize = 21.sp,
        textAlign = textAlign
    )
}
@Composable
fun SmallText (
    text: String,
    modifier: Modifier = Modifier
) {
    Text (
        text = text,
        modifier = modifier,
        fontSize = 16.sp
    )
}