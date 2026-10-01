package com.royals.class11physics

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

/** Shows a full-screen ad every [CHAPTERS_PER_AD]th time a chapter is closed, not on every close. */
class InterstitialAdManager(private val context: Context) {

    companion object {
        private const val CHAPTERS_PER_AD = 3
        private const val PREFS_NAME = "ad_prefs"
        private const val KEY_CHAPTER_CLOSE_COUNT = "chapter_close_count"
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private var interstitialAd: InterstitialAd? = null

    init {
        loadAd()
    }

    private fun loadAd() {
        InterstitialAd.load(
            context,
            BuildConfig.INTERSTITIAL_AD_UNIT_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    fun onChapterClosed(activity: Activity, onDone: () -> Unit) {
        val count = prefs.getInt(KEY_CHAPTER_CLOSE_COUNT, 0) + 1
        prefs.edit().putInt(KEY_CHAPTER_CLOSE_COUNT, count).apply()

        val ad = interstitialAd
        if (count % CHAPTERS_PER_AD == 0 && ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadAd()
                    onDone()
                }

                override fun onAdFailedToShowFullScreenContent(error: AdError) {
                    interstitialAd = null
                    loadAd()
                    onDone()
                }
            }
            ad.show(activity)
        } else {
            if (ad == null) loadAd()
            onDone()
        }
    }
}
