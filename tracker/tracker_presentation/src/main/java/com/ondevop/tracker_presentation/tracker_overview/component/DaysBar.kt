package com.ondevop.tracker_presentation.tracker_overview.component

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

@Composable
fun DaysBar(
    days: Int,
    goal: Int,
    modifier: Modifier = Modifier
){
    val background  = MaterialTheme.colorScheme.background
    val color  = MaterialTheme.colorScheme.onPrimary
    val daysExceededColor = MaterialTheme.colorScheme.error

    val daysWidthRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 =days){
        daysWidthRatio.animateTo(
            targetValue = (days.toFloat() / goal)
        )
    }

    Canvas(modifier = modifier ){
       if(days <= goal){

           /**
            * daysWidth = daysWidthRatio.value * size.width:
            * This line calculates the daysWidth based on the animation progress (daysWidthRatio.value).
            * If daysWidthRatio.value is 0, daysWidth will be 0. If daysWidthRatio.value is 1,
            * daysWidth will be equal to the entire width of the canvas (size.width).
            * If daysWidthRatio.value is 0.5, daysWidth will be half the width of the canvas
            */
           val daysWidth = daysWidthRatio.value * (size.width )

           drawRoundRect(
               color = background,
               size = size,
               cornerRadius = CornerRadius(20f)
           )

           drawRoundRect(
               color = Color.Green,
               size = Size(
                   width = daysWidth,
                   height = size.height
               ),
               cornerRadius = CornerRadius(20f)
           )
       }else{
           drawRoundRect(
               color = daysExceededColor,
               size = size,
               cornerRadius = CornerRadius(20f)
           )
       }

    }



}

