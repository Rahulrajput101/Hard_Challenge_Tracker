package com.ondevop.a75hard

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ondevop.a75hard.navigation.Route
import com.ondevop.a75hard.ui.presentation.component.DrawerBody
import com.ondevop.a75hard.ui.presentation.component.DrawerHeader
import com.ondevop.a75hard.ui.theme._75HardTheme
import com.ondevop.core.domain.prefernces.Preferences
import com.ondevop.core.uitl.Constant
import com.ondevop.core.uitl.Constant.FEEDBACK
import com.ondevop.core.uitl.Constant.PRIVACY_POLICY
import com.ondevop.core.uitl.Constant.SETTING
import com.ondevop.core.uitl.Constant.TRACKER_HOME
import com.ondevop.login_presentation.sign_in.SignInScreen
import com.ondevop.login_presentation.sign_up.SignUpScreen
import com.ondevop.settings_presentation.settings.SettingScreen
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            val isLoggedIn = preferences.getLoggedInfo().first()


            setContent {
                _75HardTheme {
                    val navController = rememberNavController()
                    val snackbarHostState = remember { SnackbarHostState() }

                    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                    val scope = rememberCoroutineScope()
                    ModalNavigationDrawer(
                        drawerContent = {
                              ModalDrawerSheet {
                                  DrawerHeader()
                                  DrawerBody(
                                      onItemClick = {item ->
                                          when(item.id){
                                              TRACKER_HOME -> {

                                              }
                                              SETTING -> {
                                                  scope.launch {
                                                      drawerState.close()
                                                  }
                                                navController.navigate(Route.Setting.route)

                                              }
                                              PRIVACY_POLICY -> {

                                              }
                                              FEEDBACK ->{

                                              }
                                          }

                                      }
                                  )

                              }

                        },
                        drawerState = drawerState
                    ) {

                        Scaffold(
                            modifier = Modifier.fillMaxSize(),
                            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                        ) {
                            NavHost(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(it),
                                navController = navController,
                                //  startDestination = if (!isLoggedIn) Route.SignIn.route else Route.TrackerHome.route
                                startDestination = Route.TrackerHome.route
                            ) {
                                composable(Route.SignIn.route) {
                                    SignInScreen(
                                        snackbarHostState = snackbarHostState,
                                        navigateToSignUp = {
                                            navController.navigate(Route.SignUp.route)
                                        },
                                        navigateToTrackerHome = {
                                            navController.navigate(Route.TrackerHome.route)
                                        },
                                        googleSignInClient = googleSignInClient
                                    )
                                }

                                composable(Route.SignUp.route) {
                                    SignUpScreen(
                                        snackbarHostState = snackbarHostState,
                                        navigateToTrackerHome = {
                                            navController.navigate(Route.TrackerHome.route)
                                        },
                                        navigateToSignIN = {
                                            navController.navigateUp()
                                        },
                                    )
                                }
                                composable(Route.TrackerHome.route) {
                                    TrackerOverViewScreen(
                                        snackbarHostState = snackbarHostState,
                                        onMenuItemClick = {
                                            scope.launch {
                                                Log.d("drawer", " ${drawerState.currentValue}")
                                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                                Log.d("drawer", " ${drawerState.currentValue}")
                                            }
                                        }

                                    )
                                }

                                composable(Route.Setting.route){
                                    SettingScreen(
                                        snackbarHostState = snackbarHostState,
                                        onNavigateBack = {
                                            navController.navigateUp()
                                            scope.launch {
                                                drawerState.open()
                                            }

                                        }

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