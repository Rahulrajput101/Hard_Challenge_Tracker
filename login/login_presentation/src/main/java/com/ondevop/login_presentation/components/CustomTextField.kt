package com.ondevop.login_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.login_presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    label : String,
    keyboardType: KeyboardType,
){
    val spacing = LocalSpacing.current
    Box(modifier = modifier){

        OutlinedTextField(
            value = text,
            onValueChange = onValueChange,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .size(60.dp)
                ,
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType
            )

        )

        IconButton(
            onClick = { },
            modifier = Modifier.align(Alignment.CenterEnd)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = com.ondevop.core.R.string.email)
            )
        }

    }


    

}