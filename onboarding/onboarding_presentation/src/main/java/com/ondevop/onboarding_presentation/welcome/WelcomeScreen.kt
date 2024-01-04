package com.ondevop.onboarding_presentation.welcome

import android.app.Activity
import android.os.Build
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ondevop.core_domain.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.WaterBlue
import com.ondevop.core_ui.WorkoutRed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun WelcomeScreen(
   onStartClick : () -> Unit
){
    
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.background))
    val progress by animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val color by infiniteTransition.animateColor(
        initialValue = WaterBlue,
        targetValue = WorkoutRed,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 5000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ){

        LottieAnimation(
            modifier = Modifier.fillMaxSize(),
            composition = composition.value,
            progress = progress,
        )

        Column(
            modifier = Modifier
                .padding(bottom = 40.dp)
                .padding(horizontal = 40.dp),
             horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.welcome),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                fontFamily = FontFamily(
                    Font(
                        R.font.rubik_light,
                        FontWeight.SemiBold
                    )
                ),
                color = MaterialTheme.colorScheme.primary,
            )

            Text(
                modifier = Modifier,
                text = stringResource(id = R.string.greeting_text),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                fontFamily = FontFamily(
                    Font(
                        R.font.rubik_light,
                        FontWeight.Medium
                    )
                ),
                color = MaterialTheme.colorScheme.primary,
            )
            
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                onClick = {
                   onStartClick()
                },
                colors = ButtonDefaults.buttonColors(containerColor = color),
                shape = CircleShape
            ) {
                Text(
                    text = "Start",
                    color = Color.Black
                )

            }

        }
    }


}