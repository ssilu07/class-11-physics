package com.royals.class11physics

import android.content.Context
import android.content.SharedPreferences

object UserDataManager {
    private const val PREFS_NAME = "user_prefs"
    private const val KEY_BOOKMARKS = "bookmarked_chapters"
    private const val KEY_BOOKMARKED_FORMULAS = "bookmarked_formulas"
    private const val KEY_BOOKMARKED_DERIVATIONS = "bookmarked_derivations"
    private const val KEY_UNLOCKED_CHAPTERS = "unlocked_chapters"
    private const val KEY_TEXT_ZOOM = "text_zoom_percent"
    private const val KEY_LAST_READ = "last_read_chapter"

    private val sessionUnlockedChapters = mutableSetOf<Int>()

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isBookmarked(context: Context, chapterNumber: Int): Boolean {
        return getBookmarkedChapters(context).contains(chapterNumber)
    }

    fun toggleBookmark(context: Context, chapterNumber: Int): Boolean {
        val current = getBookmarkedChapters(context).toMutableSet()
        val newState = if (current.contains(chapterNumber)) {
            current.remove(chapterNumber)
            false
        } else {
            current.add(chapterNumber)
            true
        }
        val stringSet = current.map { it.toString() }.toSet()
        getPrefs(context).edit().putStringSet(KEY_BOOKMARKS, stringSet).apply()
        return newState
    }

    fun getBookmarkedChapters(context: Context): Set<Int> {
        val stringSet = getPrefs(context).getStringSet(KEY_BOOKMARKS, emptySet()) ?: emptySet()
        return stringSet.mapNotNull { it.toIntOrNull() }.toSet()
    }

    fun isFormulaBookmarked(context: Context, formulaId: String): Boolean {
        return getBookmarkedFormulas(context).contains(formulaId)
    }

    fun toggleFormulaBookmark(context: Context, formulaId: String): Boolean {
        val current = getBookmarkedFormulas(context).toMutableSet()
        val newState = if (current.contains(formulaId)) {
            current.remove(formulaId)
            false
        } else {
            current.add(formulaId)
            true
        }
        getPrefs(context).edit().putStringSet(KEY_BOOKMARKED_FORMULAS, current).apply()
        return newState
    }

    fun getBookmarkedFormulas(context: Context): Set<String> {
        return getPrefs(context).getStringSet(KEY_BOOKMARKED_FORMULAS, emptySet()) ?: emptySet()
    }

    fun isDerivationBookmarked(context: Context, derivationId: String): Boolean {
        return getBookmarkedDerivations(context).contains(derivationId)
    }

    fun toggleDerivationBookmark(context: Context, derivationId: String): Boolean {
        val current = getBookmarkedDerivations(context).toMutableSet()
        val newState = if (current.contains(derivationId)) {
            current.remove(derivationId)
            false
        } else {
            current.add(derivationId)
            true
        }
        getPrefs(context).edit().putStringSet(KEY_BOOKMARKED_DERIVATIONS, current).apply()
        return newState
    }

    fun getBookmarkedDerivations(context: Context): Set<String> {
        return getPrefs(context).getStringSet(KEY_BOOKMARKED_DERIVATIONS, emptySet()) ?: emptySet()
    }


    fun isChapterUnlocked(context: Context, chapterNumber: Int, isPremium: Boolean = false): Boolean {
        if (chapterNumber <= 5) return true // Chapters 1 to 5 are completely free (Core Mechanics)
        if (isPremium) return true
        if (sessionUnlockedChapters.contains(chapterNumber)) return true
        val persisted = getPrefs(context).getStringSet(KEY_UNLOCKED_CHAPTERS, emptySet()) ?: emptySet()
        return persisted.contains(chapterNumber.toString())
    }

    fun unlockChapter(context: Context, chapterNumber: Int) {
        sessionUnlockedChapters.add(chapterNumber)
        val persisted = getPrefs(context).getStringSet(KEY_UNLOCKED_CHAPTERS, emptySet())?.toMutableSet() ?: mutableSetOf()
        persisted.add(chapterNumber.toString())
        getPrefs(context).edit().putStringSet(KEY_UNLOCKED_CHAPTERS, persisted).apply()
    }

    fun getUnlockedChapters(context: Context): Set<Int> {
        val persisted = getPrefs(context).getStringSet(KEY_UNLOCKED_CHAPTERS, emptySet()) ?: emptySet()
        val persistedInts = persisted.mapNotNull { it.toIntOrNull() }.toSet()
        return sessionUnlockedChapters + persistedInts
    }

    fun getTextZoom(context: Context): Int {
        return getPrefs(context).getInt(KEY_TEXT_ZOOM, 100)
    }

    fun setTextZoom(context: Context, zoomPercent: Int) {
        getPrefs(context).edit().putInt(KEY_TEXT_ZOOM, zoomPercent).apply()
    }

    fun getLastReadChapter(context: Context): Int {
        return getPrefs(context).getInt(KEY_LAST_READ, 1)
    }

    fun setLastReadChapter(context: Context, chapterNumber: Int) {
        getPrefs(context).edit().putInt(KEY_LAST_READ, chapterNumber).apply()
    }
}
