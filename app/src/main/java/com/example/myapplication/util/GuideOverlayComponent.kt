package com.example.myapplication.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.request.ImageRequest
import com.example.myapplication.R
import kotlinx.coroutines.delay

@Composable
fun GuideOverlayComponent(
    title: String,
    buttonText: String,
    countDownText: String? = null,
    onButtonClick: () -> Unit,

    highlightRect: Rect?,
    gifResourceId: Int? = null,
    gifSize: Dp = 60.dp,
    offsetCenterX: Int = 4,
    enableCountdown: Boolean = true
) {
    val countdown = remember { mutableStateOf(5) }
    if (enableCountdown) {
        LaunchedEffect(Unit) {
            while (countdown.value > 0) {
                delay(1000)
                countdown.value--
            }
            onButtonClick()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {}
            ),
    ) {
        val overlayColor = colorResource(id = R.color.black_50)

        /**
         * CanvasDraw CutOut Screen
         */
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
        ) {
            drawRect(color = overlayColor)
            highlightRect?.let { rect ->
                val cornerRadius = CornerRadius(15.dp.toPx())
                drawRoundRect(
                    color = Color.Transparent,
                    topLeft = rect.topLeft,
                    size = rect.size,
                    blendMode = BlendMode.Clear,
                    cornerRadius = cornerRadius
                )
            }
        }

        /**
         * Show GIF Center of CutOut Screen
         */
        val density = LocalDensity.current
        if (gifResourceId != null && highlightRect != null) {
            val imageLoader = ImageLoader.Builder(LocalContext.current)
                .components { add(GifDecoder.Factory()) }
                .build()

            val paddingInDp: Dp = gifSize

            val gifWidth = with(density) { highlightRect.size.width.toDp() } + paddingInDp
            val gifHeight = with(density) { highlightRect.size.height.toDp() } + paddingInDp
            val gifOffsetX = with(density) { highlightRect.topLeft.x.toDp() } - (paddingInDp / offsetCenterX)
            val gifOffsetY = with(density) { highlightRect.topLeft.y.toDp() } - (paddingInDp / 2)

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(gifResourceId)
                    .crossfade(true)
                    .build(),
                contentDescription = "Guide Animation",
                imageLoader = imageLoader,
                modifier = Modifier
                    .offset(x = gifOffsetX, y = gifOffsetY)
                    .size(width = gifWidth, height = gifHeight)
            )
        }

        /**
         * Guild Content
         */
        val columnModifier = if (highlightRect != null) {
            Modifier
                .padding(top = with(density) { highlightRect.bottom.toDp() } + 20.dp)
                .fillMaxWidth()
        } else {
            Modifier.align(Alignment.Center)
        }

        Column(
            modifier = columnModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                modifier = Modifier.padding(bottom = 20.dp),
                fontSize = 18.sp,
                color = colorResource(R.color.white),
                fontWeight = FontWeight(500),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            Row (
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ButtonGuildComponent(
                    modifier = Modifier,
                    buttonText = buttonText,
                    buttonBorderColor = colorResource(R.color.white),
                    onClick = onButtonClick
                )
                if (countDownText?.isNotBlank() == true) {
                    Text(
                        text = "$countDownText in (${countdown.value})",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 14.sp,
                        color = colorResource(R.color.white),
                        fontWeight = FontWeight(500),
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}