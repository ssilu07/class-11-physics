package com.royals.class11physics

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

/**
 * Schedules a daily revision reminder at 6:30 PM (peak student homework time).
 * Drives DAU retention by bringing students back every single evening.
 */
object DailyReminderManager {

    private const val PREFS_NAME = "reminder_prefs"
    private const val KEY_SCHEDULED = "is_reminder_scheduled"
    private const val REMINDER_REQUEST_CODE = 2001

    fun scheduleDailyReminder(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        val intent = Intent(context, DailyReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REMINDER_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 18) // 6:30 PM
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If 6:30 PM has already passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        try {
            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
            prefs.edit().putBoolean(KEY_SCHEDULED, true).apply()
        } catch (e: Exception) {
            // Graceful catch for SecurityException on newer Android versions
        }
    }
}
