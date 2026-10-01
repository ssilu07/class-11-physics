package com.royals.class11physics

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

/** Lets a user watch an ad to temporarily unlock a locked chapter, as an alternative to buying. */
class RewardedAdManager(private val context: Context) {

    private var rewardedAd: RewardedAd? = null

    init {
        loadAd()
    }

    private fun loadAd() {
        RewardedAd.load(
            context,
            BuildConfig.REWARDED_AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                }
            }
        )
    }

    fun show(activity: Activity, onRewardEarned: () -> Unit, onAdUnavailable: () -> Unit) {
        val ad = rewardedAd
        if (ad == null) {
            onAdUnavailable()
            return
        }
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                rewardedAd = null
                loadAd()
            }

            override fun onAdFailedToShowFullScreenContent(error: AdError) {
                rewardedAd = null
                loadAd()
                onAdUnavailable()
            }
        }
        ad.show(activity) { onRewardEarned() }
    }
}
