package com.ondevop.a75hard

import android.os.Bundle
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
import com.ondevop.login_presentation.sign_in.SignInScreen
import com.ondevop.login_presentation.sign_up.SignUpScreen
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
                                      drawerState = drawerState,
                                      onItemClick = {

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

                            }
                        }
                    }

                }

            }
        }
    }
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