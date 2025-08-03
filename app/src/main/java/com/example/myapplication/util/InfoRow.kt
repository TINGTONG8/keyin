package com.example.myapplication.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    label: String,
    value: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            modifier = Modifier,
            fontSize = 18.sp,
            fontWeight = FontWeight(400),
            fontFamily = FontFamily.SansSerif,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        Text(
            text = value,
            modifier = Modifier.weight(5f),
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.End
        )
    }
}