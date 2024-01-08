package com.ondevop.a75hard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ondevop.a75hard.navigation.Route
import com.ondevop.a75hard.ui.presentation.component.DrawerBody
import com.ondevop.a75hard.ui.presentation.component.DrawerHeader
import com.ondevop.a75hard.ui.theme._75HardTheme
import com.ondevop.core_domain.prefernces.Preferences
import com.ondevop.core_domain.uitl.Constant.FEEDBACK
import com.ondevop.core_domain.uitl.Constant.PRIVACY_POLICY
import com.ondevop.core_domain.uitl.Constant.SETTING
import com.ondevop.core_domain.uitl.Constant.TRACKER_HOME
import com.ondevop.core_domain.use_cases.SchedulingHabitAlarm
import com.ondevop.core_domain.use_cases.ToShowNotification
import com.ondevop.login_presentation.sign_in.SignInScreen
import com.ondevop.login_presentation.sign_up.SignUpScreen
import com.ondevop.onboarding_presentation.notification_allow.NotificationAllowScreen
import com.ondevop.onboarding_presentation.welcome.WelcomeScreen
import com.ondevop.settings_presentation.settings.SettingScreen
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var schedulingHabitAlarm: SchedulingHabitAlarm

    @Inject
    lateinit var toShowNotification: ToShowNotification

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {

            val splash = installSplashScreen()
            splash.setKeepOnScreenCondition { true }
            async {
                delay(1000)
                splash.setKeepOnScreenCondition { false }
            }

            val isAlarmScheduledDeferred = async { preferences.getAlarmScheduled().first() }
            val isOnboardingCompletedDeferred =
                async { preferences.getIsOnboardingCompleted().first() }
            val isLoggedInDeferred = async { preferences.getLoggedInfo().first() }

            // Wait for the results
            val isAlarmScheduled = isAlarmScheduledDeferred.await()
            val isOnboardingCompleted = isOnboardingCompletedDeferred.await()
            val isLoggedIn = isLoggedInDeferred.await()

            if (!isAlarmScheduled) {
                schedulingHabitAlarm()
            }


            setContent {
                _75HardTheme {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()

                    val profileUri by preferences.getProfileUri().collectAsState(initial = "")
                    val name by preferences.getUserName().collectAsState(initial = "")

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    ) {
                        NavHost(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it),
                            navController = navController,
                            startDestination = if (!isOnboardingCompleted) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                    Route.NotificationAllow.route
                                } else {
                                    Route.GraphAuth.route
                                }
                            } else if (!isLoggedIn) {
                                Route.GraphAuth.route
                            } else {
                                Route.GraphTracker.route
                            }
                        ) {
                            composable(Route.Welcome.route) {

                                WelcomeScreen {
                                    navController.navigate(Route.NotificationAllow.route)
                                }
                            }

                            composable(Route.NotificationAllow.route) {
                                NotificationAllowScreen(
                                    snackbarHostState = snackbarHostState,
                                    onSkipClick = {
                                        scope.launch {
                                            preferences.saveIsOnboardingCompleted(true)
                                        }
                                        navController.navigate(Route.SignIn.route) {
                                            popUpTo(Route.NotificationAllow.route) {
                                                inclusive = true
                                            }
                                        }
                                    },
                                    openAppSetting = ::openAppSettings,
                                    onShouldShowPermissionRationale = ::shouldShowRequestPermissionRationale
                                )
                            }

                            navigation(
                                startDestination = Route.SignIn.route,
                                route = Route.GraphAuth.route
                            ){
                                composable(Route.SignIn.route) {
                                    SignInScreen(
                                        snackbarHostState = snackbarHostState,
                                        navigateToSignUp = {
                                            navController.navigate(Route.SignUp.route)
                                        },
                                        navigateToTrackerHome = {
                                            navController.navigate(Route.GraphTracker.route) {
                                                popUpTo(Route.GraphAuth.route) {
                                                    inclusive = true
                                                }
                                            }
                                        },
                                        googleSignInClient = googleSignInClient
                                    )
                                }

                                composable(Route.SignUp.route) {
                                    SignUpScreen(
                                        snackbarHostState = snackbarHostState,
                                        navigateToTrackerHome = {
                                            navController.navigate(Route.GraphTracker.route) {
                                                popUpTo(Route.GraphAuth.route) {
                                                    inclusive = true
                                                }
                                            }
                                        },
                                        navigateToSignIN = {
                                            navController.navigateUp()
                                        },
                                    )
                                }

                            }

                            navigation(
                                startDestination = Route.TrackerHome.route,
                                route  = Route.GraphTracker.route
                            ){
                                composable(Route.TrackerHome.route) {
                                    ModalNavigationDrawer(
                                        drawerContent = {
                                            ModalDrawerSheet {
                                                DrawerHeader(
                                                    name = name,
                                                    imageUri = profileUri.toUri()
                                                )
                                                DrawerBody(
                                                    onItemClick = { item ->
                                                        when (item.id) {
                                                            TRACKER_HOME -> {
                                                                scope.launch {
                                                                    drawerState.close()
                                                                }
                                                            }

                                                            SETTING -> {
                                                                scope.launch {
                                                                    drawerState.close()
                                                                }
                                                                navController.navigate(Route.Setting.route)

                                                            }

                                                            PRIVACY_POLICY -> {
//                                                  scope.launch {
//                                                      drawerState.close()
//                                                  }

                                                            }

                                                            FEEDBACK -> {
//                                                  scope.launch {
//                                                      drawerState.close()
//                                                  }
                                                            }
                                                        }

                                                    }
                                                )

                                            }

                                        },
                                        drawerState = drawerState
                                    ) {
                                        TrackerOverViewScreen(
                                            snackbarHostState = snackbarHostState,
                                            onMenuItemClick = {
                                                scope.launch {
                                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                                }
                                            }

                                        )
                                    }
                                }

                                composable(Route.Setting.route) {
                                    SettingScreen(
                                        snackbarHostState = snackbarHostState,
                                        onNavigateBack = {
                                            navController.navigateUp()
                                            scope.launch {
                                                drawerState.open()

                                            }

                                        },
                                        onSignOut = {
                                            navController.navigate(Route.GraphAuth.route){
                                                popUpTo(Route.GraphTracker.route){
                                                    inclusive = true
                                                }
                                            }
                                        },
                                        openAppSettings = ::openAppSettings
                                    )
                                }
                            }

                        }
                    }
                }

            }

        }
    }


}


private fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _75HardTheme {
        Greeting("Android")
    }
}