package com.ondevop.tracker_presentation.upgrade

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ondevop.core_domain.uitl.ProVersionTypeEnum
import com.ondevop.core_domain.R
import com.ondevop.core_ui.GrayShadow
import com.ondevop.core_ui.GreenShadow
import com.ondevop.core_ui.bounceClick
import com.valentinilk.shimmer.LocalShimmerTheme
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.shimmer


@Composable
fun UpgradeScreen(
    purchaseHelper: PurchaseHelper,
    navigateToBack: () -> Unit = {},
    viewModel: UpgradeViewModel = hiltViewModel()
) {
    
    val proWeeklyPrice by purchaseHelper.proWeeklyPrice.collectAsState("")
    val proMonthlyPrice by purchaseHelper.proMonthlyPrice.collectAsState("")
    val proYearlyPrice by purchaseHelper.proYearlyPrice.collectAsState("")
    val purchased by purchaseHelper.purchased.collectAsState()
    val context = LocalContext.current
    
    LaunchedEffect(purchased)
    {
        if (purchased) {
            
            viewModel.setProVersion(true)
            Toast.makeText(
                context,
                context.resources.getString(R.string.welcome_to_pro_version),
                Toast.LENGTH_LONG
            ).show()

            
            navigateToBack()
        }
        
    }
    
    
    var showSuccessToast by remember {
        mutableStateOf(false)
    }
    var showErrorToast by remember {
        mutableStateOf(false)
    }
    
    if (showSuccessToast) {
        Toast.makeText(
            context,
            context.resources.getString(R.string.welcome_to_pro_version),
            Toast.LENGTH_LONG
        ).show()
        showSuccessToast = false
    }
    
    if (showErrorToast) {
        Toast.makeText(
            context,
            context.resources.getString(R.string.pro_version_not_purchased),
            Toast.LENGTH_LONG
        ).show()
        showErrorToast = false
    }
    
    val creditCardTheme = defaultShimmerTheme.copy(
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 1_000,
                easing = LinearEasing,
            ),
        ),
        blendMode = BlendMode.Hardlight,
        rotation = 25f,
        shaderColors = listOf(
            Color.White.copy(alpha = 0.0f),
            Color.White.copy(alpha = 0.4f),
            Color.White.copy(alpha = 0.0f),
        ),
        shaderColorStops = null,
        shimmerWidth = 150.dp,
    )


//    Column(
//        Modifier.padding(20.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//
//        Text(
//            productName,
//            Modifier.padding(20.dp),
//            fontSize = 30.sp
//        )
//
//        Text(statusText)
//
//        Row(Modifier.padding(20.dp)) {
//
//            Button(
//                onClick = { purchaseHelper.makePurchase() },
//                Modifier.padding(20.dp),
//                enabled = buyEnabled
//            ) {
//                Text("Purchase")
//            }
//
//            Button(
//                onClick = { purchaseHelper.consumePurchase() },
//                Modifier.padding(20.dp),
//                enabled = consumeEnabled
//            ) {
//                Text("Consume")
//            }
//        }
//    }
    BackHandler(true) {
    
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Icon(
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier
                    .size(30.dp)
                    .background(
                        color = GrayShadow,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .padding(5.dp)
                    .clickable{ navigateToBack() })
            
            Text(
                text = stringResource(R.string.restore),
                color = MaterialTheme.colorScheme.surface,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W600,
                    fontFamily = FontFamily(
                        Font(
                            R.font.rubik_medium,
                            FontWeight.Medium
                        )
                    ),
                    lineHeight = 25.sp
                ),
                modifier = Modifier
                    .background(
                        color = GrayShadow,
                        shape = RoundedCornerShape(90.dp)
                    )
                    .padding(vertical = 5.dp, horizontal = 10.dp)
                    .clickable {
                        purchaseHelper.restorePurchase {
                            if (it.not()) {
                                showErrorToast = true
                            }
                        }
                    }
            )
            
        }
        
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.app_icon),
                contentDescription = stringResource(R.string.app_name),
                tint = Green,
                modifier = Modifier.size(width = 150.dp, height = 100.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    color = MaterialTheme.colorScheme.surface,
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.Medium
                            )
                        ),
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = stringResource(R.string.pro),
                    color = MaterialTheme.colorScheme.primary,
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.W700,
                        fontFamily = FontFamily(
                            Font(
                                R.font.rubik_medium,
                                FontWeight.Medium
                            )
                        ),
                        lineHeight = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .background(GreenShadow, shape = RoundedCornerShape(90.dp))
                        .padding(horizontal = 9.dp)
                )
                
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(20.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = GreenShadow,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.powered_by_chat_gpt),
                        color = MaterialTheme.colorScheme.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = GreenShadow,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.unlock_url),
                        color = MaterialTheme.colorScheme.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = GreenShadow,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.unlock_scan),
                        color = MaterialTheme.colorScheme.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = GreenShadow,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.unlimited_messages),
                        color = MaterialTheme.colorScheme.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .size(25.dp)
                            .background(
                                color = GreenShadow,
                                shape = RoundedCornerShape(90.dp)
                            )
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.cancel_anytime),
                        color = MaterialTheme.colorScheme.surface,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W600,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }
            }
            
            if (proWeeklyPrice != "" && proMonthlyPrice != "" && proYearlyPrice != "") {
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    
                    Column(
                        modifier = Modifier
                            .bounceClick {
                                purchaseHelper.makePurchase(ProVersionTypeEnum.WEEKLY)
                            }
                            .weight(1f)
                            .background(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    
                    ) {
                        Text(
                            text = stringResource(R.string.weekly),
                            color = MaterialTheme.colorScheme.primary,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(15.dp)
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.secondary, thickness = 1.dp
                        )
                        Text(
                            text = proWeeklyPrice,
                            color = MaterialTheme.colorScheme.surface,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(15.dp)
                        
                        )
                        
                        Text(
                            text = stringResource(id = R.string.per_week),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W500,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                        
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(
                        modifier = Modifier
                            .bounceClick {
                                purchaseHelper.makePurchase(ProVersionTypeEnum.MONTHLY)
                            }
                            .weight(1f)
                            .background(
                                shape = RoundedCornerShape(16.dp),
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                            .border(
                                1.dp,
                                color = MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(16.dp)
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    
                    ) {
                        Text(
                            text = stringResource(R.string.monthly),
                            color = MaterialTheme.colorScheme.primary,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(15.dp)
                        )
                        Divider(
                            color = MaterialTheme.colorScheme.secondary, thickness = 1.dp
                        )
                        Text(
                            text = proMonthlyPrice,
                            color = MaterialTheme.colorScheme.surface,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(15.dp)
                        
                        )
                        
                        Text(
                            text = stringResource(id = R.string.per_month),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W500,
                                fontFamily =FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                        
                        )
                    }
                    
                }
                
                Spacer(modifier = Modifier.height(10.dp))
                
                CompositionLocalProvider(
                    LocalShimmerTheme provides creditCardTheme
                ) {
                    
                    Box(contentAlignment = Alignment.TopEnd, modifier = Modifier.bounceClick {
                        purchaseHelper.makePurchase(ProVersionTypeEnum.YEARLY)
                        
                    }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    2.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(16.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onSecondary
                            ),
                            shape = RoundedCornerShape(16.dp),
                            
                            ) {
                            Column(
                                modifier = Modifier.shimmer(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            )
                            {
                                
                                
                                Text(
                                    text = stringResource(R.string.yearly),
                                    color = MaterialTheme.colorScheme.primary,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W600,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.rubik_medium,
                                                FontWeight.Medium
                                            )
                                        ),
                                        lineHeight = 25.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(15.dp)
                                )
                                
                                Divider(
                                    color = MaterialTheme.colorScheme.secondary, thickness = 1.dp
                                )
                                Text(
                                    text = proYearlyPrice,
                                    color = MaterialTheme.colorScheme.surface,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.W600,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.rubik_medium,
                                                FontWeight.Medium
                                            )
                                        ),
                                        lineHeight = 25.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(15.dp)
                                
                                )
                                
                                Text(
                                    text = stringResource(id = R.string.per_year),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.W500,
                                        fontFamily = FontFamily(
                                            Font(
                                                R.font.rubik_medium,
                                                FontWeight.Medium
                                            )
                                        ),
                                        lineHeight = 25.sp
                                    ),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(bottom = 15.dp)
                                
                                )
                            }
                        }
                        
                        Text(
                            text = stringResource(R.string.save85),
                            color = MaterialTheme.colorScheme.surface,
                            style = TextStyle(
                                fontSize = 12.sp,
                                fontWeight = FontWeight.W600,
                                fontFamily = FontFamily(
                                    Font(
                                        R.font.rubik_medium,
                                        FontWeight.Medium
                                    )
                                ),
                                lineHeight = 25.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .background(
                                    color = Green,
                                    shape = RoundedCornerShape(
                                        topEnd = 16.dp,
                                        topStart = 0.dp,
                                        bottomEnd = 0.dp,
                                        bottomStart = 10.dp
                                    )
                                )
                                .padding(vertical = 5.dp, horizontal = 10.dp)
                        )
                        
                    }
                }
                
                Spacer(modifier = Modifier.height(30.dp))
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.shield_done),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .size(35.dp)
                            .padding(5.dp)
                    )
                    Text(
                        text = stringResource(R.string.secured_by_google_play),
                        color = MaterialTheme.colorScheme.surface,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily(
                                Font(
                                    R.font.rubik_medium,
                                    FontWeight.Medium
                                )
                            ),
                            lineHeight = 25.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }
            
            
        }
        
        
    }
}