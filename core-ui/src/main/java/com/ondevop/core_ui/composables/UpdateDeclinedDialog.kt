package com.ondevop.core_ui.composables

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.ondevop.core_domain.R
import com.ondevop.core_ui.LocalSpacing

@Composable
@Preview(showBackground = true)
fun UpdateDeclinedDialogPreview() {
    // Modify the parameters as needed for your composable
    UpdateDeclinedDialog(
        modifier = Modifier.wrapContentSize(),
        isDialogShowing = true,
        onUpdate = {},
        onDismiss = {}
    )
}

@Composable
fun UpdateDeclinedDialog(
    modifier: Modifier = Modifier,
    isDialogShowing: Boolean,
    onUpdate: () -> Unit,
    onDismiss: () -> Unit
) {
    val spacing = LocalSpacing.current

    if (isDialogShowing) {
        Dialog(
            onDismissRequest = { onDismiss() },
        ) {
            Card(
                modifier = modifier,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    Image(
                        painter = painterResource(id = R.drawable.magic_hat),
                        contentDescription = ""
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.made_updates),
                        style = MaterialTheme.typography.headlineSmall,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_light,
                                FontWeight.Medium
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.made_updates_description),
                        style = MaterialTheme.typography.bodySmall,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.Medium
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        letterSpacing = (0.5).sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(40.dp))
                                .background(MaterialTheme.colorScheme.primary)
                                .clickable {
                                    onUpdate()
                                }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.update_now),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }

                    }
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                }
            }
        }
    }
}