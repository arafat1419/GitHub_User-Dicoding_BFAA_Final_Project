package com.dicoding.sub3_githubusers.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.sub3_githubusers.R
import com.dicoding.sub3_githubusers.data.model.Reminder
import com.dicoding.sub3_githubusers.databinding.FragmentSettingsBinding
import com.dicoding.sub3_githubusers.preference.ReminderPreference
import com.dicoding.sub3_githubusers.ui.activity.AboutActivity
import com.dicoding.sub3_githubusers.ui.fitur.AlarmReceiver


class SettingsFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Settings"

        val alarmReceiver = AlarmReceiver()

        remindCheck()

        binding.btnAbout.setOnClickListener(this)
        binding.btnConfig.setOnClickListener(this)
        binding.switchAlarm.setOnCheckedChangeListener { _, checked ->
            if (checked) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(context)
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(context)
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_about -> {
                Intent(activity, AboutActivity::class.java).also {
                    activity?.startActivity(it)
                }
            }
            R.id.btn_config -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                activity?.startActivity(intent)
            }
        }
    }

    private fun remindCheck() {
        val reminderPreference = ReminderPreference(context)
        binding.switchAlarm.isChecked = reminderPreference.getRemind().isRemind
    }

    private fun saveReminder(state: Boolean) {
        val reminderPreference = ReminderPreference(context)
        val reminder = Reminder()
        reminder.isRemind = state
        reminderPreference.setRemind(reminder)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}