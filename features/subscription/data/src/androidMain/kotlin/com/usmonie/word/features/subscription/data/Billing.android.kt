package com.usmonie.word.features.subscription.data

import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.usmonie.word.features.subscription.domain.models.SubscriptionStatus

actual class Billing(private val billingClientBuilder: BillingClient.Builder) {
    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        // To be implemented in a later section.
    }
    private val billingClient by lazy {
        billingClientBuilder
            .enablePendingPurchases()
            .setListener(purchasesUpdatedListener)
            .build()
    }

    private val subscribers: MutableList<(SubscriptionStatus) -> Unit> = mutableListOf()

    private var started: Boolean = false

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

    actual fun subscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        subscribers.add(onSubscriptionChanged)
        onSubscriptionChanged(SubscriptionStatus.PURCHASED)
    }

    actual fun unsubscribeSubscriptionState(onSubscriptionChanged: (SubscriptionStatus) -> Unit) {
        subscribers.remove(onSubscriptionChanged)
    }

    private fun checkSubscriptionStatus() {
        val queryProductDetailsParams = QueryProductDetailsParams.newBuilder()
            .setProductList(
                listOf(
                    QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("product_id_example")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()
                )
            )
            .build()

        billingClient
            .queryProductDetailsAsync(queryProductDetailsParams) { billingResult,
                                                                   productDetailsList ->
            }
    }
}
