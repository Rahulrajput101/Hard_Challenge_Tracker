package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ondevop.core.R
import com.ondevop.core_ui.pictureViolet
import com.ondevop.tracker_presentation.tracker_overview.TrackerOverViewState

@Composable
fun PictureCardView(
    state : TrackerOverViewState,
    onCardClick: () -> Unit,
    hasButton : Boolean,
    onTakePictureClick:() ->Boolean,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            state = state,
            backgroundImage =  R.drawable.camera_svg,
            heading = R.string.click_picture,
            subHeading = R.string.daily_photo_goal,
            buttonText = R.string.take_photo,
            cardColor = pictureViolet,
            hasImage = true,
            hasButton = hasButton,
            onButtonClick = {
              onTakePictureClick()
            },
            onCardClick = {
                onCardClick()
            })
    }

}