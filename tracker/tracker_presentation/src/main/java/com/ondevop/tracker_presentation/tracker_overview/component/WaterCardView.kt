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
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {



        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(12.dp)),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, WaterBlue),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            )
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                // Background image with blur effect
                Image(
                    painter = painterResource(id = R.drawable.water_droplet_svgrepo_com),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp))
                        .alpha(0.7f) // Adjust the alpha as needed for the blur effect
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .padding(spacing.spaceSmall),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(id = R.string.drink_water),
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Bold
                                    )
                                )
                            )
                            Text(
                                text = stringResource(id = R.string.daily_goal_water),
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.SemiBold
                                    )
                                )
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clickable { onDrinkClick() }
                                .clip(RoundedCornerShape(10.dp))
                                .background(WaterBlue)
                                .padding(vertical = 8.dp, horizontal = 18.dp)
                        ) {
                            Text(
                                text = stringResource(id = R.string.drink_1l),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            horizontalArrangement = Arrangement.End
        ){
            Box(
                modifier = Modifier
                    .clickable { onDrinkClick() }
                    .clip(RoundedCornerShape(10.dp))
                    .background(WaterBlue)
                    .padding(vertical = 8.dp, horizontal = 18.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.drink_track),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

        }


    }

}
