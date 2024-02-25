package com.ondevop.settings_presentation.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_domain.R
import com.ondevop.core_domain.uitl.UiEvent
import com.ondevop.core_ui.LocalSpacing


@Composable
fun SettingScreen(
    viewModel: SettingViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit,
    onSignOut: () -> Unit,
    openAppSettings: () -> Unit
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val signInChecked by remember { mutableStateOf(true) }

    var hasNotificationPermissions by rememberSaveable {
        mutableStateOf(false)
    }


    LaunchedEffect(key1 = Unit) {
        hasNotificationPermissions =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true
            }
    }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    DisposableEffect(context) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back press event from Android shortcut
                onNavigateBack()
            }
        }

        backDispatcher?.addCallback(callback)

        onDispose {
            // Remove the callback when the composable is disposed
            callback.remove()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }

                is UiEvent.Success -> {
                    onSignOut()
                }

                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 4.dp)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back),
                        )

                    }

                    Text(
                        text = stringResource(id = R.string.setting),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.SemiBold
                            )
                        ),
                        letterSpacing = (0.2).sp
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(start = 8.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = stringResource(id = R.string.allow_notification),
                            style = MaterialTheme.typography.bodyLarge,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )

                        Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

                        Text(
                            text = stringResource(id = R.string.notification_reason),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Normal
                                )
                            ),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                    }
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))

                    Switch(
                        checked = hasNotificationPermissions,
                        onCheckedChange = {
                            openAppSettings()
                        },
                    )

                }

                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .padding(start = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = stringResource(id = R.string.log_out),
                        fontSize = 20.sp,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.Medium
                            )
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))

                    Switch(
                        checked = signInChecked,
                        onCheckedChange = {
                            viewModel.onEvent(SettingEvent.SignOut)
                        },
                    )

                }

            }

        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Add some additional padding at the bottom
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                tint = MaterialTheme.colorScheme.error,
                contentDescription = stringResource(id = R.string.back),
            )

            Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
            ClickableText(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_light,
                                    FontWeight.Normal
                                )
                            ),
                        )
                    ) {
                        append(stringResource(id = R.string.delete_my_account))
                    }
                },
                onClick = {
                    try {
                        val intent =
                            Intent(Intent.ACTION_SENDTO).apply {
                                data =
                                    Uri.parse("mailto:") // only email apps should handle this
                                putExtra(
                                    Intent.EXTRA_EMAIL,
                                    arrayOf("rahulsingh4959199@gmail.com")
                                )
                                putExtra(Intent.EXTRA_SUBJECT, "Account Removal Request")
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Please delete my account. Thanks."
                                )
                            }
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                },
            )
        }
    }
}
