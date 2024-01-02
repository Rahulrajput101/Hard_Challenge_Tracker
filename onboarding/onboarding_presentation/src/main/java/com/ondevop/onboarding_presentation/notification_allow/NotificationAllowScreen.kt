package com.ondevop.onboarding_presentation.notification_allow

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_domain.R
import com.ondevop.core_domain.uitl.Permission
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.composables.CameraPermissionTextProvider
import com.ondevop.core_ui.composables.NotificationPermissionTextProvider
import com.ondevop.core_ui.composables.PermissionDialog


@Composable
fun NotificationAllowScreen(
    viewModel : NotificationAllowViewModel= hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onShouldShowPermissionRationale: (String) -> Boolean,
    openAppSetting: () -> Unit,
    onSkipClick: () -> Unit,

) {
    val spacing = LocalSpacing.current
    val dialogQueue = viewModel.visiblePermissionDialogQueue
     val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
         arrayOf(
             Permission.POST_NOTIFICATIONS
        )
     } else {
         arrayOf(
         )
     }


    val multiplePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions() ,
        onResult = {perms ->
            permissionsToRequest.forEach { permissions ->
                viewModel.onPermissionResult(
                    permission = permissions ,
                    isGranted = perms[permissions.toPermissionString()] == true
                )
            }

        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.spaceMedium)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.skip),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = FontFamily(
                        Font(
                            R.font.rubik_medium,
                            FontWeight.Normal
                        )
                    ),
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable {
                     onSkipClick()
                    }
                    .padding(2.dp)
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.notification_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(150.dp)
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))


                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.notification),
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_light,
                                FontWeight.Medium
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceSmall))
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.notification_description),
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

                }

            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),

            ) {

            }

            FloatingActionButton(
                onClick = {
                    multiplePermissionLauncher.launch(
                        permissionsToRequest.map {
                            it.toPermissionString()
                        }.toTypedArray()
                    )

                },
                modifier = Modifier
                    .fillMaxWidth(),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = stringResource(id = R.string.approve),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 18.sp
                )
            }

            dialogQueue
                .reversed()
                .forEach {permission ->
                    PermissionDialog(
                        permissionTextProvider = when(permission){
                           Permission.POST_NOTIFICATIONS-> NotificationPermissionTextProvider()
                            else -> return@forEach
                        },
                        isPermanentlyDeclined = !onShouldShowPermissionRationale(permission.toPermissionString()),
                        onDismissClick =  viewModel::dismissDialog,
                        onOkClick = {
                                    viewModel.dismissDialog()
                            multiplePermissionLauncher.launch(
                                arrayOf(permission.toPermissionString())
                            )
                        },
                        onGoToAppSettingsClicks = openAppSetting,
                    )
            }

        }

    }


}