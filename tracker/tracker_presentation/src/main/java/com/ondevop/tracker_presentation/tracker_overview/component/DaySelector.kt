package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ondevop.core_domain.R
import java.time.LocalDate

@Composable
fun DaySelector(
    date: LocalDate,
    onPreviousDayClick: () -> Unit,
    onNextDayClick: () -> Unit,
    selectedDayIsFirstDay: Boolean,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = onPreviousDayClick,
            enabled = !selectedDayIsFirstDay,
            modifier = Modifier
                .alpha(if (selectedDayIsFirstDay) 0.5f else 1f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.previous_day)
            )

        }

        Text(
            text = parseDateText(date = date),
            style = MaterialTheme.typography.bodyLarge,
            fontFamily = FontFamily(
                Font(
                    R.font.rubik_medium,
                    FontWeight.SemiBold
                )
            )
        )

        IconButton(
            onClick = onNextDayClick,
            enabled = date != LocalDate.now(),
            modifier = Modifier
                .alpha(if (date == LocalDate.now()) 0.5f else 1f)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = stringResource(id = R.string.next_day)
            )
        }

    }
}