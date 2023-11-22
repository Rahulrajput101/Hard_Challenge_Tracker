package com.ondevop.login_presentation.sign_in

import android.app.Activity
import android.service.autofill.UserData
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.login_presentation.components.CustomTextField
import com.ondevop.core.uitl.UiEvent
import com.ondevop.login_presentation.components.RoundedButton


@Composable
fun SignInScreen(
    snackbarHostState: SnackbarHostState,
    navigateToSignUp: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToTrackerHome: () -> Unit,
    googleSignInClient: GoogleSignInClient
) {


    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.NavigateUp -> {
                }
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }

                UiEvent.Success -> {
                    navigateToTrackerHome()
                }
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    val userData = account.id?.let {
                        Log.d("MyTag","tovs: im ${account.photoUrl}")
                        Log.d("MyTag","tovs: im ${account.displayName}")
                        UserData(
                            userName = account.displayName,
                            profilePictureUrl = account.photoUrl.toString(),
                        )

                    }
                    if(userData != null){
                        viewModel.onEvent(SignInEvent.SaveUserdata(userData))
                    }

                } else {
                    // Handle sign-in failure
                    viewModel.onEvent(SignInEvent.SignInUnsuccessful("Failed to sign in"))
                }
            } catch (e: ApiException) {
                viewModel.onEvent(SignInEvent.SignInUnsuccessful("$e"))
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            // Sign-in was canceled
            viewModel.onEvent(SignInEvent.SignInUnsuccessful("Sign-in canceled"))
            // Sign-in was canceled
        } else {
            // Handle other result codes or errors, if needed
            viewModel.onEvent(SignInEvent.SignInUnsuccessful("Sign-in failed with error code ${result.resultCode}"))
        }
    }




    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.proceed),
                    style = MaterialTheme.typography.headlineMedium,
                    fontFamily = FontFamily(
                        Font(
                            R.font.rubik_medium,
                            FontWeight.W200
                        )
                    )
                )
                Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))
                Text(
                    text = stringResource(id = R.string.login_),
                    style = MaterialTheme.typography.headlineLarge,
                    fontFamily = FontFamily(
                        Font(
                            R.font.rubik_medium,
                            FontWeight.Bold
                        )
                    )
                )
            }

            Spacer(modifier = Modifier.width(spacing.spaceMedium))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.human_login),
                    contentDescription = stringResource(id = R.string.login_illustration),
                    modifier = Modifier
                        .size(250.dp) // Adjust the height as needed
                        .fillMaxWidth()
                        .clickable {
                            navigateToTrackerHome()
                        }
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            CustomTextField(
                text = state.email,
                onValueChange = {
                    viewModel.onEvent(
                        SignInEvent.UpdateEmail(it)
                    )
                },
                icon = Icons.Default.Email,
                label = stringResource(id = R.string.email),
                keyboardType = KeyboardType.Email,
                modifier = Modifier.padding(4.dp)
            )
            if(state.emailError != null){
                Text(
                    text = state.emailError!!.asString(context),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            CustomTextField(
                text = state.password,
                onValueChange = {
                    viewModel.onEvent(
                        SignInEvent.UpdatePassword(it)
                    )
                },
                icon = Icons.Default.Lock,
                label = stringResource(id = R.string.password),
                keyboardType = KeyboardType.Password,
                modifier = Modifier.padding(4.dp)
            )
            if(state.passwordError != null){
                Text(
                    text = state.passwordError!!.asString(context),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(SignInEvent.SignInClick)
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = stringResource(id = R.string.login),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.do_not_have_account),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(id = R.string.sign_up),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue,
                    modifier = Modifier.clickable {
                        navigateToSignUp()
                    }
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = stringResource(id = R.string.or),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column {
                    RoundedButton(
                        text = "G",
                        onClick = {
                         launcher.launch(googleSignInClient.signInIntent)
                        }
                    )
                    Text(
                        text = stringResource(id = R.string.google),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 3.dp)
                    )
                }

            }

        }
    }

}