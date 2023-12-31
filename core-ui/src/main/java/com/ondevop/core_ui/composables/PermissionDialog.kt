package com.ondevop.core_ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.ondevop.core_ui.LocalSpacing

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismissClick: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClicks: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    AlertDialog(
        onDismissRequest = {
           onDismissClick()
        },
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Divider()
                Text(
                    text = if (isPermanentlyDeclined) {
                        "Grant"
                    } else {
                        "ok"
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            if (isPermanentlyDeclined) {
                                onGoToAppSettingsClicks()
                            } else {
                                onOkClick()
                            }
                        }
                        .padding(spacing.spaceMedium)
                )

            }
        },
        text = {
               Text(
                   text = permissionTextProvider.getDescription(
                       isPermanentlyDeclined = isPermanentlyDeclined
                   )
               )

        },
        title = {
                Text(text = "Permission Required")

        },
    )

}

interface PermissionTextProvider {
    fun getDescription( isPermanentlyDeclined : Boolean) : String
}
class CameraPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined camera permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs access to your camera so that your friends " +
                    "can see you in a call."
        }
    }
}
class NotificationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined) {
            "It seems you permanently declined notification permission. " +
                    "You can go to the app settings to grant it."
        } else {
            "This app needs to notify you about your task so you will be consistent."
        }
    }
}
