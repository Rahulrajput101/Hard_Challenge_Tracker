package com.ondevop.core_ui.composables

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import androidx.core.net.toUri
import coil.Coil
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.ondevop.core.R
import com.ondevop.core_ui.LocalSpacing

@Preview
@Composable
fun CircularImagePreview() {
   // CircularImage(iconsResId = R.drawable.reunion_interview_svgrepo_com)
}

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    imageUri: Uri?,
    size: Dp = 120.dp,
    onClick :() -> Unit
) {

    val spacing = LocalSpacing.current

    Box(
        modifier = modifier
            .padding(
                top = spacing.spaceExtraSmall,
                bottom = spacing.spaceExtraSmall,
                end = spacing.spaceSmall
            )
            .size(size)
            .clip(RoundedCornerShape(400.dp))
            .border(
                width = 1.dp,
                color = Color.White,
                shape = RoundedCornerShape(400.dp)
            )
            .clickable { onClick() }
        ) {

        if(imageUri != null){
//            Image(
//                painter = // Adjust scale as needed
//                rememberAsyncImagePainter(
//                    ImageRequest.Builder(LocalContext.current).data(data = imageUri)
//                        .apply<ImageRequest.Builder>(block = fun ImageRequest.Builder.() {
//                            scale(Scale.FILL)  // Adjust scale as needed
//                        }).build()
//                ),
//                contentDescription = null,
//                contentScale = ContentScale.Crop, // or other ContentScale options
//                modifier = Modifier.fillMaxWidth()
//            )
            Image(
                painter = rememberImagePainter(
                    data = imageUri,
                    builder = {
                        // Add a unique key to force Coil to fetch a new image
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
            )

        }else{
            Image(
                painter = painterResource(id = R.drawable.profile__circle),
                contentDescription = null
            )
        }


    }



}
