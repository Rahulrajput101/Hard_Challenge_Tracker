package com.ondevop.tracker_presentation.upgrade

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.input.key.Key.Companion.D
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.AcknowledgePurchaseResponseListener
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.ConsumeParams
import com.android.billingclient.api.ConsumeResponseListener
import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList
import com.ondevop.core_domain.uitl.Constant
import com.ondevop.core_domain.uitl.ProVersionTypeEnum
import com.ondevop.core_domain.uitl.Security
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class PurchaseHelper(val context: Context) {

    private lateinit var billingClient: BillingClient
    private lateinit var productDetails: ProductDetails
    private lateinit var purchase: Purchase

    private val _statusText = MutableStateFlow("Initializing...")
    val statusText = _statusText.asStateFlow()

    private val _purchased = MutableStateFlow(false)
    val purchased = _purchased.asStateFlow()

    private val _proWeeklyPrice = MutableStateFlow("")
    val proWeeklyPrice = _proWeeklyPrice.asStateFlow()

    private val _proMonthlyPrice = MutableStateFlow("")
    val proMonthlyPrice = _proMonthlyPrice.asStateFlow()

    private val _proYearlyPrice = MutableStateFlow("")
    val proYearlyPrice = _proYearlyPrice.asStateFlow()

    private val _buyEnabled = MutableStateFlow(false)
    val buyEnabled = _buyEnabled.asStateFlow()

    fun initializeBillingClient() {
        billingClient = BillingClient.newBuilder(context)
            .setListener(purchasesUpdatedListener)
            .enablePendingPurchases()
            .build()

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(
                billingResult: BillingResult
            ) {
                if (billingResult.responseCode ==
                    BillingClient.BillingResponseCode.OK
                ) {
                    _statusText.value = "Billing Client Connected"
                    queryProduct(Constant.PRODUCT_ID)
                } else {
                    _statusText.value = "Billing Client Connection Failure"
                }
            }

            override fun onBillingServiceDisconnected() {
                _statusText.value = "Billing Client Connection Lost"
            }
        })
    }

    private fun queryProduct(productId: String) {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                ImmutableList.of(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId(productId)
                        .setProductType(
                            BillingClient.ProductType.SUBS
                        )
                        .build()
                )
            )
            .build()


        billingClient.queryProductDetailsAsync(queryProductDetailsParams) {
                billingResult,
                productDetailsList ->
            // check billingResult
            // process returned productDetailsList
            billingClient.queryProductDetailsAsync(
                queryProductDetailsParams
            ) { billingResult, productDetailsList ->
                if (productDetailsList.isNotEmpty()) {
                    productDetails = productDetailsList[0]

                    val weeklyPricing =
                        productDetails.subscriptionOfferDetails?.find { it.basePlanId == Constant.WEEKLY_BASE_PLAN }?.pricingPhases?.pricingPhaseList?.getOrNull(
                            0
                        )?.formattedPrice
                    val monthlyPricing =
                        productDetails.subscriptionOfferDetails?.find { it.basePlanId == Constant.MONTHLY_BASE_PLAN }?.pricingPhases?.pricingPhaseList?.getOrNull(
                            0
                        )?.formattedPrice
                    val yearlyPricing =
                        productDetails.subscriptionOfferDetails?.find { it.basePlanId == Constant.YEARLY_BASE_PLAN }?.pricingPhases?.pricingPhaseList?.getOrNull(
                            0
                        )?.formattedPrice

                    if (weeklyPricing != null) {
                        _proWeeklyPrice.value = weeklyPricing
                    }
                    if (monthlyPricing != null) {
                        _proMonthlyPrice.value = monthlyPricing
                    }
                    if (yearlyPricing != null) {
                        _proYearlyPrice.value = yearlyPricing
                    }

                } else {
                    _statusText.value = "No Matching Products Found"
                    _buyEnabled.value = false
                }
            }
        }
    }

    //for trial base
//    suspend fun getPrices(): Triple<String, String, String> =
//        suspendCoroutine { continuation ->
//            var dur: String
//            var phases: String
//            var subsName = ""
//            var des = ""
//            //  val billingClient = getOrCreateBillingClient()
//            billingClient.startConnection(object : BillingClientStateListener {
//                override fun onBillingSetupFinished(billingResult: BillingResult) {
//                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
//                        val queryProductDetailsParams =
//                            QueryProductDetailsParams.newBuilder()
//                                .setProductList(
//                                    listOf(
//                                        QueryProductDetailsParams.Product.newBuilder()
//                                            .setProductId("basic")
//                                            .setProductType(BillingClient.ProductType.SUBS)
//                                            .build()
//                                    )
//                                )
//                                .build()
//
//                        billingClient.queryProductDetailsAsync(queryProductDetailsParams) { billingResult, productDetailsList ->
//                            productDetailsList?.forEach { productDetails ->
//                                val offerToken =
//                                    productDetails.subscriptionOfferDetails?.get(0)?.offerToken
//
//                                subsName = productDetails.name
//                                des = productDetails.description
//                                val formattedPrice = productDetails.subscriptionOfferDetails!![0]
//                                    .pricingPhases.pricingPhaseList[0].formattedPrice
//                                val billingPeriod = productDetails.subscriptionOfferDetails!![0]
//                                    .pricingPhases.pricingPhaseList[0].billingPeriod
//                                val recurrenceMode = productDetails.subscriptionOfferDetails!![0]
//                                    .pricingPhases.pricingPhaseList[0].recurrenceMode
//
//                                val n = billingPeriod.substring(1, 2)
//                                val duration = billingPeriod.substring(2, 3)
//
//                                if (recurrenceMode == 2) {
//                                    dur = when (duration) {
//                                        "M" -> "For $n Month"
//                                        "Y" -> "For $n Year"
//                                        "W" -> "For $n Week"
//                                        "D" -> "For $n Days"
//                                        else -> "Unknown Duration"
//                                    }
//                                } else {
//                                    dur = when (billingPeriod) {
//                                        "P1M" -> "/Monthly"
//                                        "P6M" -> "/Every 6 Month"
//                                        "P1Y" -> "/Yearly"
//                                        "P1W" -> "/Weekly"
//                                        "P3W" -> "/Every 3 Weeks"
//                                        else -> ""
//                                    }
//                                }
//
//                                phases = "$formattedPrice $dur"
//                                var size =
//                                    productDetails.subscriptionOfferDetails!![0].pricingPhases.pricingPhaseList.size
//                                for (i in 0 until productDetails.subscriptionOfferDetails!![0].pricingPhases.pricingPhaseList.size) {
//                                    if (i > 0) {
//                                        val period = productDetails.subscriptionOfferDetails!![0]
//                                            .pricingPhases.pricingPhaseList[i].billingPeriod
//
//                                        val price = productDetails.subscriptionOfferDetails!![0]
//                                            .pricingPhases.pricingPhaseList[i].formattedPrice
//
//                                        dur = when (period) {
//                                            "P1M" -> "/Monthly"
//                                            "P6M" -> "/Every 6 Month"
//                                            "P1Y" -> "/Yearly"
//                                            "P1W" -> "/Weekly"
//                                            "P3W" -> "/Every 3 Weeks"
//                                            else -> ""
//                                        }
//
//                                        phases += "\n" + price + dur
//                                    }
//                                }
//
//                                Log.d("MyTag", "Billing client connected successfully")
//                                continuation.resume(Triple(subsName, phases, des))
//                            }
//                        }
//                    }
//                }
//
//                override fun onBillingServiceDisconnected() {
//                    // Try to restart the connection on the next request to
//                    // Google Play by calling the startConnection() method.
//                    continuation.resumeWith(Result.failure(Exception("Billing service disconnected")))
//                }
//            })
//        }


    private val purchasesUpdatedListener =
        PurchasesUpdatedListener { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK && purchases != null) {

                for(purchase in purchases){

                    handlePurchase(purchase)
                }
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.USER_CANCELED ) {
                 _statusText.value = "Purchase Canceled"
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                _statusText.value = "ITEM_ALREADY_OWNED"
                //Toast.makeText(context, "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show()
            } else if (billingResult.responseCode == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED) {
                _statusText.value = "FEATURE_NOT_SUPPORTED"
              //  Toast.makeText(context, "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show()
            } else {
                   _statusText.value = "Purchase Error"
            }
        }

    private fun handlePurchase(purchase: Purchase) {

        val consumeParams = ConsumeParams.newBuilder()
                    .setPurchaseToken(purchase.purchaseToken)
                    .build()

        val listener = ConsumeResponseListener{ billingResult, _ ->
            if(billingResult.responseCode == BillingClient.BillingResponseCode.OK){

            }
        }

        billingClient.consumeAsync(consumeParams,listener)

        // Check the purchase state and handle it accordingly
        if(purchase.purchaseState == Purchase.PurchaseState.PURCHASED){

            /** Verify the signature of the purchase data to
             * ensure its authenticity **/
            if(!verifyValidSignature(purchase.originalJson,purchase.signature)){
                Toast.makeText(context,"Error: Invalid purchase",Toast.LENGTH_SHORT).show()
                return
            }

            if(!purchase.isAcknowledged){
                acknowledgePurchase(purchase)
            }

        }else if(purchase.purchaseState == Purchase.PurchaseState.PENDING){
            //set the subscription text pending subscribtion
        }else if(purchase.purchaseState == Purchase.PurchaseState.UNSPECIFIED_STATE){
            //set the subscription text pending unspecififed state
        }
    }

    var acknowledgePurchaseResponseListener = AcknowledgePurchaseResponseListener{ billingResult ->
        // Inside acknowledgePurchaseResponseListener
        Log.d("MyTag", "Acknowledgment response: $billingResult")
        if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
            Toast.makeText(context, "Subscription acknowledged successfully", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(context, "Error acknowledging purchase: ${billingResult.responseCode}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
            .setPurchaseToken(purchase.purchaseToken)
            .build()
        billingClient.acknowledgePurchase(acknowledgePurchaseParams){billingResult ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                _purchased.value = true
                try {

                    val date = Date(purchase.purchaseTime)
                    val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
                    val purchaseTime =   format.format(date)

                    val orderId = purchase.orderId
                    val purchaseToken = purchase.purchaseToken
                    val product = purchase.products[0]

                    val params = Bundle()
                    params.putString("order_id", orderId as String?)
                    params.putString("purchase_time", purchaseTime)
                    params.putString("purchase_token", purchaseToken as String?)
                    params.putString("product", product)

                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }
        //set the subscrbe text subscribed
    }

    private fun verifyValidSignature(signedData: String,signature: String) : Boolean{
        return try {
            val base64Key =""
            val security = Security()
            security.verifyPurchases(base64Key,signedData,signature)
        }catch (e : IOException){
            false
        }
    }

    fun makePurchase(proType: ProVersionTypeEnum) {
        val subOfferToken = when (proType) {
            ProVersionTypeEnum.WEEKLY -> productDetails.subscriptionOfferDetails!!.find { it.basePlanId == Constant.WEEKLY_BASE_PLAN }!!.offerToken
            ProVersionTypeEnum.MONTHLY -> productDetails.subscriptionOfferDetails!!.find { it.basePlanId == Constant.MONTHLY_BASE_PLAN }!!.offerToken
            ProVersionTypeEnum.YEARLY -> productDetails.subscriptionOfferDetails!!.find { it.basePlanId == Constant.YEARLY_BASE_PLAN }!!.offerToken
        }

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(
                ImmutableList.of(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                        .setProductDetails(productDetails)
                        .setOfferToken(
                            subOfferToken
                        )
                        .build()
                )
            )
            .build()

        billingClient.launchBillingFlow(context as Activity , billingFlowParams)
    }

    fun restorePurchase(callback: (Boolean) -> Unit) {
        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()

        var isPurchased = false

        billingClient.queryPurchasesAsync(queryPurchasesParams) { _, purchases ->
            if (purchases.isNotEmpty()) {
                purchase = purchases.first()
                _purchased.value = true
                isPurchased = true
            } else {
                _purchased.value = false
                isPurchased = false
            }
            callback(isPurchased)
        }
    }

    fun checkPurchase(callback: (Boolean) -> Unit) {
        val queryPurchasesParams = QueryPurchasesParams.newBuilder()
            .setProductType(BillingClient.ProductType.SUBS)
            .build()
        var isPurchased = false

        billingClient.queryPurchasesAsync(queryPurchasesParams) { _, purchases ->
            if (purchases.isNotEmpty()) {
                purchase = purchases.first()
                isPurchased = true
            } else {
                isPurchased = false
            }
            callback(isPurchased)
        }
    }



}