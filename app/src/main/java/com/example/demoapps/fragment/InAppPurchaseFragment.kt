package com.example.demoapps.fragment

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.android.billingclient.api.*
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentInAppPurchaseBinding


class InAppPurchaseFragment : Fragment() {
    private lateinit var dataBinding: FragmentInAppPurchaseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_in_app_purchase,
            container,
            false
        )
        setClick()
        val view = dataBinding.root
        return view

    }

    private fun setClick() {


        dataBinding.btnPurchase.setOnClickListener {
            val purchaseUpdateListener = PurchasesUpdatedListener { billingResult, purchase ->
            }

            var billingClient = BillingClient.newBuilder(requireContext())
                .setListener(purchaseUpdateListener)
                .enablePendingPurchases()
                .build()
            billingClient.startConnection(object : BillingClientStateListener {
                override fun onBillingServiceDisconnected() {

                }

                override fun onBillingSetupFinished(billingResult: BillingResult) {
                    if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                        val skuList = ArrayList<String>()
                        skuList.add("premium_upgrade")
                        val params = SkuDetailsParams.newBuilder().setSkusList(skuList)
                            .setType(BillingClient.SkuType.INAPP)


                        billingClient.querySkuDetailsAsync(params.build()) { billingResult, skuDetailslist ->
                            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                                for (skudata in skuDetailslist!!) {
                                    val flowPurchase = BillingFlowParams.newBuilder()
                                        .setSkuDetails(skudata)
                                        .build()

                                    billingClient.launchBillingFlow(
                                        requireContext() as Activity,
                                        flowPurchase
                                    ).responseCode
                                }
                            }
                        }
                    }
                }
            })
        }
    }


}