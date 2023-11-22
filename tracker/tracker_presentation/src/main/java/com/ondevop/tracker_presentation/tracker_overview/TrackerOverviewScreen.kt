package com.ondevop.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.tracker_presentation.tracker_overview.component.TrackerHeader

@Composable
fun TrackerOverViewScreen(
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val spacing = LocalSpacing.current

   LazyColumn(
       modifier = Modifier
           .fillMaxWidth()
           .padding(bottom = spacing.spaceMedium)
   ){
       item {
           TrackerHeader(
               state = state
           )
       }


   }



}

