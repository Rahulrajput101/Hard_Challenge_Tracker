package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ondevop.core_domain.R
import com.ondevop.core_ui.LocalSpacing

@Composable
fun PhotoOptionDialog(
    modifier: Modifier = Modifier,
    onCameraClick: () -> Unit,
    onPickerClick: () -> Unit,
    isDialogShowing: Boolean,
    onDismiss: () -> Unit
) {
    val spacing = LocalSpacing.current

    if (isDialogShowing) {
        Dialog(onDismissRequest = onDismiss) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = "camera",

                            )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        Text(
                            text = stringResource(id = R.string.click_picture),
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.SemiBold
                                )
                            )
                        )

                    }
                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.picker),
                            contentDescription = "image picker",

                            )
                        Spacer(modifier = Modifier.width(spacing.spaceSmall))
                        Text(
                            text = stringResource(id = R.string.click_picture),
                            modifier = Modifier,
                            style = MaterialTheme.typography.bodyMedium,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.SemiBold
                                )
                            )
                        )

                    }
                }
            }
        }
    }
}