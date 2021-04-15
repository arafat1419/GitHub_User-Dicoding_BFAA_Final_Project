package com.dicoding.sub3_githubusers.ui.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.sub3_githubusers.R
import com.dicoding.sub3_githubusers.ui.fragment.FollowersFragment
import com.dicoding.sub3_githubusers.ui.fragment.FollowingFragment

class FollowPagerAdapter(
    private val context: Context,
    fragmentManager: FragmentManager,
    data: Bundle
) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle = data

    @StringRes
    private val tabTitles = intArrayOf(R.string.tab_followers, R.string.tab_following)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }

        fragment?.arguments = this.fragmentBundle

        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(tabTitles[position])
    }
}