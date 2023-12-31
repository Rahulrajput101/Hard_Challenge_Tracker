package com.ondevop.core_ui.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.ondevop.core_domain.R

@Composable
fun SmallCircleImage(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
){
    Box(   modifier = Modifier
        .clip(CircleShape),
        contentAlignment = Alignment.Center,

    ) {
        if(imageUri != null && imageUri.toString().isNotEmpty()){
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current).data(data = imageUri).apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }).build()
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        }else{
            Image(
                painter = painterResource(id = R.drawable.profile__circle),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )
        }

    }

}