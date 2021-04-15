package com.dicoding.sub3_githubusers.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.sub3_githubusers.databinding.ActivityFollowBinding
import com.dicoding.sub3_githubusers.ui.activity.DetailActivity.Companion.EXTRA_USERNAME
import com.dicoding.sub3_githubusers.ui.adapter.FollowPagerAdapter

class FollowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFollowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME, username)

        val followPagerAdapter = FollowPagerAdapter(this, supportFragmentManager, mBundle)
        binding.apply {
            followViewPager.adapter = followPagerAdapter
            followTab.setupWithViewPager(followViewPager)
        }
    }
}