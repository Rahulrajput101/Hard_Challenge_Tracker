package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing

@Composable
fun GreetingSection(
    name: String = "Adam",
    modifier: Modifier = Modifier,
) {
    val spacing = LocalSpacing.current

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                horizontal = spacing.spaceSmall,
                vertical = spacing.spaceMedium
            )

    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(
                    id = R.string.welcome_text,
                    name
                ),
                color =MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = stringResource(
                    id = R.string.greeting_text
                ),
                color =MaterialTheme.colorScheme.onPrimary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light
            )
        }

    }


}
