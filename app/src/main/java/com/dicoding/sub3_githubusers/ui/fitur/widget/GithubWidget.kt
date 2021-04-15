package com.dicoding.sub3_githubusers.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.dicoding.sub3_githubusers.R

class GithubWidget : AppWidgetProvider() {

    companion object {
        private const val TOAST_ACTION = "com.dicoding.sub3_githubusers.TOAST_ACTION"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, WidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val remoteViews = RemoteViews(context.packageName, R.layout.widget_layout)
            remoteViews.setRemoteAdapter(R.id.appwidget_list, intent)

            val toast = Intent(context, WidgetService::class.java)
            toast.apply {
                action = TOAST_ACTION
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val pendingToast =
                PendingIntent.getBroadcast(context, 0, toast, PendingIntent.FLAG_UPDATE_CURRENT)
            remoteViews.setPendingIntentTemplate(R.id.appwidget_list, pendingToast)

            appWidgetManager.apply {
                notifyAppWidgetViewDataChanged(appWidgetId, R.layout.widget_layout)
                updateAppWidget(appWidgetId, remoteViews)
            }
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }
    }

    override fun onEnabled(context: Context) {
        val message = context.resources.getString(R.string.widget_enabled)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDisabled(context: Context) {
        val message = context.resources.getString(R.string.widget_disabled)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action != null) {
            if (intent.action == TOAST_ACTION) {
                Toast.makeText(context, "Your Favorite GithubUser", Toast.LENGTH_SHORT).show()
            }
        }
    }
}