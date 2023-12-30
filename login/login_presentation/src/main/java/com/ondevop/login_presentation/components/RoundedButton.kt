package com.ondevop.login_presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ondevop.core_domain.R
import com.ondevop.core_ui.LocalSpacing

@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Box(
        modifier = Modifier
            .size(50.dp)
            .padding(4.dp)
            .clip(RoundedCornerShape(200.dp))
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(200.dp)
            )
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                onClick()
            }
            ,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            fontFamily = FontFamily(
                Font(
                    R.font.rubik_medium,
                    FontWeight.Bold
                )
            ),
        )
    }

}