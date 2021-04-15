package com.dicoding.sub3_githubusers.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sub3_githubusers.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        setTitle(R.string.about_me)
    }
}