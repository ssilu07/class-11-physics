package com.royals.class11physics

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class DailyReminderReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "daily_physics_revision"
        const val NOTIFICATION_ID = 1101
    }

    override fun onReceive(context: Context, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            DailyReminderManager.scheduleDailyReminder(context)
            return
        }

        showNotification(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Daily Physics Revision",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Daily 5-minute NCERT Physics formula & derivation revision reminders"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val titles = listOf(
            "⚡ Quick Physics Revision",
            "📖 Class 11 Physics Check-in",
            "🎯 5-Min Exam Prep",
            "🔬 Today's Physics Formulas"
        )
        val messages = listOf(
            "Quick revision of Formulas & Derivations! Master today's key concepts in 5 mins.",
            "Take 5 minutes to revise key NCERT physics formulas before your evening study session.",
            "Stay consistent! Check top formulas and exam derivations for Class 11 Physics.",
            "Exam booster: Review laws of motion, gravitation & thermodynamics formulas now!"
        )

        val index = (System.currentTimeMillis() % titles.size).toInt()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.class_eleven_logo)
            .setContentTitle(titles[index])
            .setContentText(messages[index])
            .setStyle(NotificationCompat.BigTextStyle().bigText(messages[index]))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }
}
