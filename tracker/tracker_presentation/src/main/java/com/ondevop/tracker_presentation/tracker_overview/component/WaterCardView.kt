package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.WaterBlue
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverviewEvent

@Preview(
    showSystemUi = false
)
@Composable
fun WaterCardPreview() {

    WaterCardView(
        drinkWater = 50,
        waterGoal = 4,
        onDrinkClick = { },
        onCardClick = { })

}
@Composable
fun WaterCardView(
    drinkWater: Int,
    waterGoal: Int,
    onDrinkClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            heading = R.string.drink_water,
            subHeading =R.string.daily_goal_water ,
            buttonText = R.string.drink_1l,
            cardColor = WaterBlue,
            hasImage = true,
            onButtonClick = {
                onDrinkClick()
            },
            onCardClick = {

            })

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 30.dp),
            horizontalArrangement = Arrangement.End
        ){
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(WaterBlue)
                    .padding(vertical = 6.dp, horizontal = 18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.drink_track,
                        drinkWater
                    ),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

        }


    }

}
