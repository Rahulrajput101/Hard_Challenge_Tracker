package com.ondevop.a75hard.ui.presentation.component

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ondevop.core_domain.uitl.Constant
import com.ondevop.core_domain.R
import com.ondevop.core_ui.LocalSpacing
import com.ondevop.core_ui.composables.AnimatedBorder
import com.ondevop.core_ui.composables.SmallCircleImage
import java.util.UUID
import kotlin.random.Random

@Composable
fun DrawerHeader(
    name : String,
    imageUri : Uri? = null
){

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 60.dp),
    ){
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AnimatedBorder(
                modifier = Modifier
                    .size(100.dp),
                borderWidth = 4.dp
            ) {
               SmallCircleImage(
                   modifier = Modifier
                       .clip(CircleShape),
                   imageUri = if(imageUri.toString().isNotEmpty()) imageUri else null
               )
            }

            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.welcome),
                style = MaterialTheme.typography.bodyMedium,
                fontFamily = FontFamily(
                    Font(
                        R.font.rubik_medium,
                        FontWeight.Bold
                    )
                ),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = name ,
                style = MaterialTheme.typography.headlineSmall,
                fontFamily = FontFamily(
                    Font(
                        R.font.rubik_black,
                        FontWeight.Bold
                    )
                ),
                textAlign = TextAlign.Center,
            )



        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerBody(
    modifier: Modifier = Modifier,
    itemTextStyle : TextStyle = TextStyle(fontSize = 18.sp),
    onItemClick: (NavigationItem) -> Unit
) {
    var selectedItemIndex = -1
    val spacing = LocalSpacing.current
    val items = listOf(
        NavigationItem(
            id = Constant.TRACKER_HOME,
            title = Constant.TRACKER_HOME,
            selectedIcon = Icons.Filled.Home,
            unSelectedIcon = Icons.Outlined.Home,
            contentDescription = Constant.TRACKER_HOME
        ),
//        NavigationItem(
//            id = Constant.RATE_US,
//            title = Constant.RATE_US,
//            selectedIcon = Icons.Filled.Star,
//            unSelectedIcon = Icons.Outlined.Star,
//            contentDescription = Constant.RATE_US
//        ),
        NavigationItem(
            id = Constant.FEEDBACK,
            title = Constant.FEEDBACK,
            selectedIcon = Icons.Filled.Email,
            unSelectedIcon = Icons.Outlined.Email,
            contentDescription = Constant.FEEDBACK
        ),
        NavigationItem(
            id = Constant.SETTING,
            title = Constant.SETTING,
            selectedIcon = Icons.Filled.Settings,
            unSelectedIcon = Icons.Outlined.Settings,
            contentDescription = Constant.SETTING
        ),
        NavigationItem(
            id = Constant.PRIVACY_POLICY,
            title = Constant.PRIVACY_POLICY,
            selectedIcon = Icons.Filled.PrivacyTip,
            unSelectedIcon = Icons.Outlined.PrivacyTip,
            contentDescription = Constant.PRIVACY_POLICY
        )
    )

    ModalDrawerSheet {
        Spacer(modifier = modifier.height(spacing.spaceMedium))
        LazyColumn {
            items.forEachIndexed { index, item ->
                item(
                    key = item.id
                ) {
                    Spacer(modifier = modifier.height(spacing.spaceExtraSmall))
                    NavigationDrawerItem(
                        label = {
                            Text(
                                text = item.title,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Bold
                                    )
                                )
                            )
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            onItemClick(item)
                            selectedItemIndex = index
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unSelectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)

                    )
                }
            }
        }
    }
}


