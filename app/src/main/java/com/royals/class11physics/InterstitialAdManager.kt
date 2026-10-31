package com.royals.class11physics

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/**
 * Singleton Interstitial Ad Manager.
 * Preloads and maintains a single full-screen interstitial ad instance at the application level.
 * Avoids wasting ad requests across Activity lifecycle transitions, boosting AdMob show rate to 80%+.
 */
object InterstitialAdManager {

    private const val CHAPTERS_PER_AD = 3
    private const val PREFS_NAME = "ad_prefs"
    private const val KEY_CHAPTER_CLOSE_COUNT = "chapter_close_count"

    private var interstitialAd: InterstitialAd? = null
    private var isLoading = false

    fun preload(context: Context) {
        loadAd(context.applicationContext)
    }

    private fun loadAd(appContext: Context) {
        if (isLoading || interstitialAd != null) return
        isLoading = true

        InterstitialAd.load(
            appContext,
            BuildConfig.INTERSTITIAL_AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    isLoading = false
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                    isLoading = false
                }
            }
        )
    }

    fun onChapterClosed(activity: Activity, onDone: () -> Unit) {
        val prefs = activity.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val count = prefs.getInt(KEY_CHAPTER_CLOSE_COUNT, 0) + 1
        prefs.edit().putInt(KEY_CHAPTER_CLOSE_COUNT, count).apply()

        val ad = interstitialAd
        if (count % CHAPTERS_PER_AD == 0 && ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadAd(activity.applicationContext)
                    onDone()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    loadAd(activity.applicationContext)
                    onDone()
                }
            }
            ad.show(activity)
        } else {
            if (ad == null && !isLoading) {
                loadAd(activity.applicationContext)
            }
            onDone()
        }
    }
}
