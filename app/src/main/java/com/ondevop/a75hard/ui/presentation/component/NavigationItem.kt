package com.ondevop.a75hard.ui.presentation.component

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem(
    val id: String,
    val title : String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val contentDescription: String,
    val badgeCount: Int? = null
)
