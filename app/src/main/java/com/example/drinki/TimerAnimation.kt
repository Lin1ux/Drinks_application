package com.example.drinki

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TimerAnimation(
    run : Boolean
)
{
    //
    val circles = listOf(
        remember { Animatable(initialValue = 0.0f) },
        remember { Animatable(initialValue = 0.0f) },
        remember { Animatable(initialValue = 0.0f) },
    )

    if(run)
    {
        circles.forEachIndexed { index,animatable ->
            LaunchedEffect(key1 = animatable) {
                delay(index * 100L)
                animatable.animateTo(
                    targetValue = 1.0f,
                    animationSpec = infiniteRepeatable(
                        animation = keyframes {
                            durationMillis = 1000
                            0.0f at 0 with LinearOutSlowInEasing
                            1.0f at 250 with LinearOutSlowInEasing
                            0.0f at 500 with LinearOutSlowInEasing
                            0.0f at 1000 with LinearOutSlowInEasing
                        },
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }
    }

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) {20.dp.toPx()}
    val lastCircle = circleValues.size + 1

    Row(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically)
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

            if(index != lastCircle)
            {
                Spacer(modifier = Modifier.width(10.dp))
            }
        }
    }

}