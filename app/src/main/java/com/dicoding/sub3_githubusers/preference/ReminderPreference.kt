package com.dicoding.sub3_githubusers.preference

import android.content.Context
import com.dicoding.sub3_githubusers.data.model.Reminder

class ReminderPreference(context: Context?) {

    private val preference = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setRemind(value: Reminder) {
        val editor = preference?.edit()
        editor?.putBoolean(REMINDER, value.isRemind)
        editor?.apply()
    }

    fun getRemind(): Reminder {
        val model = Reminder()
        if (preference != null) {
            model.isRemind = preference.getBoolean(REMINDER, false)
        }
        return model
    }


    companion object {
        const val PREFS_NAME = "reminder_pref"
        private const val REMINDER = "isRemind"
    }
}