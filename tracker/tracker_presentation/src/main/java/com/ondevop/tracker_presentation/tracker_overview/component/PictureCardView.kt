package com.ondevop.tracker_presentation.tracker_overview.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ondevop.core.R
import com.ondevop.core_ui.pictureViolet

@Composable
fun PictureCardView(
    onCardClick: () -> Unit,
    onTakePictureClick:() ->Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp),
    ) {
        CustomCardView(
            backgroundImage =  R.drawable.camera_svg,
            heading = R.string.click_picture,
            subHeading = R.string.daily_photo_goal,
            buttonText = R.string.take_photo,
            cardColor = pictureViolet,
            hasImage = true,
            onButtonClick = {

            },
            onCardClick = {
                onCardClick()
            })
    }

}