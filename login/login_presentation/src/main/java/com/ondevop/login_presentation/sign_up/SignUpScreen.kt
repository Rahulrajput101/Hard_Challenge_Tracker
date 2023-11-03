package com.ondevop.login_presentation.sign_up

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.login_presentation.components.CustomTextField

@Preview(showSystemUi = true)
@Composable
fun PreviewSignIn(){
    SignUpScreen()
}

@Composable
fun SignUpScreen() {

    val spacing = LocalSpacing.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.create_your_account),
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
                    text = stringResource(id = R.string.sign_up_),
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
                    .padding(end = 20.dp)
                ,
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = painterResource(id = R.drawable.human_login),
                    contentDescription = stringResource(id = R.string.login_illustration),
                    modifier = Modifier
                        .size(250.dp) // Adjust the height as needed
                        .fillMaxWidth()
                )
            }


            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            CustomTextField(
                text = "" ,
                onValueChange = {},
                icon = Icons.Default.Email,
                label = stringResource(id = R.string.email),
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.height(spacing.spaceSmall))

            CustomTextField(
                text ="" ,
                onValueChange = {},
                icon = Icons.Default.Lock,
                label = stringResource(id = R.string.password),
                modifier = Modifier.padding(4.dp)
            )

            Spacer(modifier = Modifier.height(spacing.spaceExtraLarge))
            FloatingActionButton(
                onClick = {
                    {}
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(55.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = stringResource(id = R.string.create_account),
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
                    text = stringResource(id = R.string.already_have_account),
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue,
                    modifier = Modifier.clickable {

                    }
                )
            }
        }
    }

}