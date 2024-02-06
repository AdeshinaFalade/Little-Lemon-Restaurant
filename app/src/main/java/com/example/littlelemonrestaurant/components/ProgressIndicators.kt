package com.example.littlelemonrestaurant.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun AlternatingSwappingCircles(
    modifier: Modifier = Modifier,
    circleSize: Float = 40f,
    circleSpacing: Float = 2f,
    colors: List<Color> = listOf(Color.Cyan, Color.Red),
    animationDuration: Int = 1000
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val space = (circleSize / 2) + circleSpacing
    val firstCircleOffset by infiniteTransition.animateFloat(
        initialValue = -space,
        targetValue = space,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val secondCircleOffset by infiniteTransition.animateFloat(
        initialValue = space,
        targetValue = -space,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    val progress = (space - secondCircleOffset.absoluteValue) / space
    val minSize = circleSize * 0.4f / 2
    val maxSize = circleSize / 2
    val secondCircleRadius = lerp(maxSize, minSize, progress)

    Canvas(modifier = modifier) {
        val centerY = size.height / 2
        val centerX = size.width / 2

        drawCircle(
            color = colors[0],
            center = Offset(centerX + firstCircleOffset, centerY),
            radius = circleSize / 2
        )

        drawCircle(
            color = colors[1],
            center = Offset(centerX + secondCircleOffset, centerY),
            radius = secondCircleRadius
        )
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun DisplayLoader(modifier: Modifier = Modifier) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    Surface(
        color = Color.Black.copy(alpha = 0.6f),
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x00000000))
                .clickable(
                    interactionSource = mutableInteractionSource,
                    indication = null,
                    onClick = {}
                ),
            contentAlignment = Alignment.Center
        ) {
            AlternatingSwappingCircles(modifier = modifier)
        }
    }
}