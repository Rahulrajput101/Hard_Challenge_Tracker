package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.WaterBlue

@Composable
fun CustomCardView(
    backgroundImage: Int = R.drawable.water_droplet_svgrepo_com,
    heading: Int,
    subHeading: Int,
    buttonText: Int,
    cardColor : Color,
    hasImage: Boolean = false,
    hasButton: Boolean = true,
    onButtonClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(130.dp)
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onCardClick()
            },
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, cardColor),
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
            if(hasImage){
                Image(
                    painter = painterResource(id = backgroundImage),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    alpha = 0.5f,
                    modifier = Modifier
                        .fillMaxSize()
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))

                )
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(spacing.spaceSmall)
                    .size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = heading),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Bold
                                )
                            )
                        )
                        Text(
                            text = stringResource(id = subHeading),
                            style = MaterialTheme.typography.bodySmall,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.SemiBold
                                )
                            )
                        )
                    }

                    if(hasButton){
                        Box(
                            modifier = Modifier
                                .clickable { onButtonClick() }
                                .clip(RoundedCornerShape(10.dp))
                                .background(cardColor)
                                .padding(vertical = 8.dp, horizontal = 18.dp),
                        ) {
                            Text(
                                text = stringResource(id = buttonText),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                            )
                        }
                    }

                }
            }
        }
    }
}