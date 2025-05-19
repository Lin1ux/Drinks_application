package com.example.drinki

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun TimerAnimation()
{
    //
    val circles = listOf(
        remember { Animatable(initialValue = 0.0f) },
        remember { Animatable(initialValue = 0.0f) },
        remember { Animatable(initialValue = 0.0f) },
    )

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) {20.dp.toPx()}

    Row(modifier = Modifier.fillMaxWidth())
    {
        circleValues.forEachIndexed { index, value ->
            Box(
                modifier = Modifier
                    .size(25.dp)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                    )
            )
        }
    }

}