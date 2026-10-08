package com.royals.class11physics

import android.app.Activity
import android.content.Context
import com.google.android.play.core.review.ReviewManagerFactory

/**
 * Manages Google Play In-App Review API.
 * Prompts active users for ratings after they read chapters, directly boosting ASO rating signals.
 */
class InAppReviewManager(private val context: Context) {

    companion object {
        private const val PREFS_NAME = "review_prefs"
        private const val KEY_READ_COUNT = "chapters_read_count"
        private const val KEY_LAST_PROMPT_TIME = "last_prompt_timestamp"
        private const val MIN_READS_BEFORE_FIRST_PROMPT = 2
        private const val READS_BETWEEN_PROMPTS = 5
        private const val MIN_DAYS_BETWEEN_PROMPTS_MS = 7L * 24 * 60 * 60 * 1000 // 7 days
    }

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val reviewManager = ReviewManagerFactory.create(context)

    /**
     * Call this when a student finishes reading a chapter.
     * Increments the read count and launches the in-app review flow if criteria are met.
     */
    fun onChapterRead(activity: Activity) {
        val currentCount = prefs.getInt(KEY_READ_COUNT, 0) + 1
        val lastPrompt = prefs.getLong(KEY_LAST_PROMPT_TIME, 0L)
        val now = System.currentTimeMillis()

        prefs.edit().putInt(KEY_READ_COUNT, currentCount).apply()

        val isEligible = when {
            currentCount == MIN_READS_BEFORE_FIRST_PROMPT -> true
            currentCount > MIN_READS_BEFORE_FIRST_PROMPT && 
                (currentCount - MIN_READS_BEFORE_FIRST_PROMPT) % READS_BETWEEN_PROMPTS == 0 &&
                (now - lastPrompt) > MIN_DAYS_BETWEEN_PROMPTS_MS -> true
            else -> false
        }

        if (isEligible) {
            requestAndLaunchReview(activity)
        }
    }

    private fun requestAndLaunchReview(activity: Activity) {
        val request = reviewManager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                prefs.edit().putLong(KEY_LAST_PROMPT_TIME, System.currentTimeMillis()).apply()
                reviewManager.launchReviewFlow(activity, reviewInfo)
            }
        }
    }
}
