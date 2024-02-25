package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_domain.R



@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun previewCompleteDialog(){
  CompleteDialog(
      isDialogShowing =  true,
      onRestart = { /*TODO*/ },
      onMoveForward = { /*TODO*/ }) {
      
  }
}
@Composable
fun CompleteDialog(
    modifier: Modifier = Modifier,
    isDialogShowing: Boolean,
    onRestart: () -> Unit,
    onMoveForward: () -> Unit,
    onDismiss: () ->Unit
) {
    val spacing = LocalSpacing.current
    if (isDialogShowing) {
        Dialog(
            onDismissRequest = {  onDismiss() },
        ) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Text(
                        text = "Hurray!",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.Green,
                        style = MaterialTheme.typography.headlineMedium,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.SemiBold
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Divider()
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    Image(
                        painter = painterResource(id = R.drawable.celebration),
                        contentDescription = "celebration Image",

                        )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Text(
                        text = "You have completed your goal",
                        modifier = Modifier,
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.SemiBold
                            )
                        )
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceLarge))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable {
                                    onRestart()
                                }
                                .padding(vertical = 10.dp, horizontal = 18.dp),
                        ) {
                            Text(
                                text = "Restart",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable {
                                    onMoveForward()
                                }
                                .padding(vertical = 10.dp, horizontal = 18.dp),
                        ) {
                            Text(
                                text = "Move forward",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }

                    }

                }

            }
        }
    }
}