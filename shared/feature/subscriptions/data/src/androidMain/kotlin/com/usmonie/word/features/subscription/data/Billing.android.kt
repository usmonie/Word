package com.usmonie.word.features.subscription.data

import android.app.Activity
import android.util.Log
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.android.billingclient.api.QueryPurchasesParams
import com.android.billingclient.api.queryProductDetails
import com.usmonie.core.domain.BillingConfig
import com.usmonie.core.domain.tools.fastForEach
import com.usmonie.core.domain.tools.fastMap
import com.usmonie.word.features.subscription.domain.models.Subscription
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

actual class Billing(private val activity: Activity) {
    private val subscribers: MutableList<(SubscriptionStatus) -> Unit> = mutableListOf()

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        // To be implemented in a later section.
        purchases?.fastForEach {
            subscribers.fastForEach {
                it(SubscriptionStatus.None)
            }
        }
    }

    private val billingClient by lazy {
        BillingClient.newBuilder(activity)
            .enablePendingPurchases()
            .setListener(purchasesUpdatedListener)
            .build()
    }

    private val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
        .setProductList(
            listOf(
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(BillingConfig.monthlySubscriptionId)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(BillingConfig.halfYearSubscriptionId)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build(),
                QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(BillingConfig.yearlySubscriptionId)
                    .setProductType(BillingClient.ProductType.SUBS)
                    .build()
            )
        )
        .build()

    init {
        // Initialize the BillingClient
        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    // Billing client is ready
                    checkSubscriptionStatus()

                }
            }

            override fun onBillingServiceDisconnected() {
                // Retry connection or handle the error
            }
        })
    }

    actual suspend fun getSubscriptions(): List<Subscription> {
        val result = billingClient.queryProductDetails(queryProductDetailsParams)

        result.productDetailsList?.fastMap { sub ->
            sub.subscriptionOfferDetails?.fastForEach {
            }
        } ?: listOf()

        return emptyList()
    }

    actual fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        onSubscriptionChanged(SubscriptionStatus.None)
        subscribers.add(onSubscriptionChanged)
    }

    actual fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        subscribers.remove(onSubscriptionChanged)
    }

    actual suspend fun subscribe(subscription: Subscription) {
        Log.d("SubscribingProcess", subscription.toString())
        val queryProductDetailsParams =
            QueryProductDetailsParams.newBuilder()
                .setProductList(
                    listOf(
                        when (subscription) {
                            is Subscription.HalfYear ->
                                QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId(BillingConfig.halfYearSubscriptionId)
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build()

                            is Subscription.Monthly ->
                                QueryProductDetailsParams.Product.newBuilder()
                                    .setProductId(BillingConfig.monthlySubscriptionId)
                                    .setProductType(BillingClient.ProductType.SUBS)
                                    .build()

                            is Subscription.Year -> QueryProductDetailsParams.Product.newBuilder()
                                .setProductId(BillingConfig.yearlySubscriptionId)
                                .setProductType(BillingClient.ProductType.SUBS)
                                .build()
                        }
                    )
                )
                .build()
        Log.d("SubscribingProcess", queryProductDetailsParams.toString())

        val product = billingClient.queryProductDetails(queryProductDetailsParams)
            .productDetailsList?.get(0) ?: return
        Log.d("SubscribingProcess", product.toString())

        val productDetailsParamsList = listOf(
            BillingFlowParams.ProductDetailsParams.newBuilder()
                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                .setProductDetails(product)
                // For One-time product, "setOfferToken" method shouldn't be called.
                // For subscriptions, to get an offer token, call ProductDetails.subscriptionOfferDetails()
                // for a list of offers that are available to the user
                .setOfferToken(product.subscriptionOfferDetails?.get(0)?.offerToken ?: return)
                .build()
        )

        val billingFlowParams = BillingFlowParams.newBuilder()
            .setProductDetailsParamsList(productDetailsParamsList)
            .build()

        billingClient.launchBillingFlow(activity, billingFlowParams)
    }

    private fun checkSubscriptionStatus() {
        billingClient.queryPurchasesAsync(
            QueryPurchasesParams.newBuilder()
                .setProductType(BillingClient.ProductType.SUBS).build()
        ) { billingResult, purchases ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                for (purchase in purchases) {
//                    if (purchase.skus.contains(productId) && purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
//                        callback(true)
//                        return@queryPurchasesAsync
//                    }
                }
//                callback(false)
            } else {
//                callback(false)
            }
        }
    }
}
