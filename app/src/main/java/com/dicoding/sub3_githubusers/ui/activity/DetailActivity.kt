package com.dicoding.sub3_githubusers.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dicoding.sub3_githubusers.R
import com.dicoding.sub3_githubusers.data.model.Repos
import com.dicoding.sub3_githubusers.databinding.ActivityDetailBinding
import com.dicoding.sub3_githubusers.ui.adapter.FollowPagerAdapter
import com.dicoding.sub3_githubusers.ui.adapter.ReposAdapter
import com.dicoding.sub3_githubusers.ui.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private lateinit var reposAdapter: ReposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setActionBar(resources.getString(R.string.detail, username))

        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME, username)

        adapterConfig()

        viewModel = ViewModelProvider(this).get(
            DetailViewModel::class.java
        )

        if (username != null) {
            viewModel.setDetailUser(username)
            viewModel.setListRepos(username)
        }

        binding.apply {
            rvRepos.layoutManager =
                GridLayoutManager(this@DetailActivity, 1, GridLayoutManager.HORIZONTAL, false)
            rvRepos.setHasFixedSize(true)
            rvRepos.adapter = reposAdapter
        }

        viewModelConfig()

        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main) {
                if (count == 1) {
                    binding.toggleFav.isChecked = true
                    _isChecked = true
                } else {
                    binding.toggleFav.isChecked = false
                    _isChecked = false
                }
            }
        }

        binding.toggleFav.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked) {
                viewModel.addToFavorite(username, id, avatarUrl)
                Toast.makeText(
                    this,
                    resources.getString(R.string.add_fav, username),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.removeFromFavorite(id)
                Toast.makeText(
                    this,
                    resources.getString(R.string.remove_fav, username),
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.toggleFav.isChecked = _isChecked
        }

        val sectionPagerAdapter = FollowPagerAdapter(this, supportFragmentManager, mBundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabLayout.setupWithViewPager(viewPager)
        }
    }

    private fun viewModelConfig() {
        viewModel.getListRepos().observe(this, {
            Log.d("Data Reposititory", it.toString())
            if (it != null) {
                reposAdapter.setList(it)
            }
        })

        viewModel.getDetailUser().observe(this, {
            Log.d("Data User", it.toString())
            if (it != null) {
                binding.apply {
                    txtUsername.text = it.login
                    txtId.text = it.id.toString()
                    txtFollowers.text = it.followers.toString()
                    txtFollowing.text = it.following.toString()
                    txtRepos.text = it.repos.toString()
                    txtCompany.text = it.company
                    txtLocation.text = it.location
                    txtBlog.text = it.blog
                    if (it.company == null) {
                        txtCompany.text = getString(R.string.empty)
                    }
                    if (it.location == null) {
                        txtLocation.text = getString(R.string.empty)
                    }
                    if (it.blog == null) {
                        txtBlog.text = getString(R.string.empty)
                    }
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .apply(RequestOptions().transform(CenterCrop(), RoundedCorners(20)))
                        .into(imgUser)
                    val url = it.htmlUrl
                    binding.fab.setOnClickListener {
                        val uri = Uri.parse(url)
                        val gitIntent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(gitIntent)
                    }
                }
            }
        })
    }

    private fun adapterConfig() {
        reposAdapter = ReposAdapter()
        reposAdapter.notifyDataSetChanged()

        reposAdapter.setOnItemClickCallback(object : ReposAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Repos) {
                val uri = Uri.parse(data.htmlUrl)
                val reposIntent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(reposIntent)
                Toast.makeText(
                    this@DetailActivity,
                    resources.getString(R.string.choose, data.htmlUrl),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    private fun ImageView.loadImage(url: Int) {
        Glide.with(this.context)
            .load(url)
            .apply(RequestOptions().override(18, 18))
            .centerCrop()
            .into(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val infalter = menuInflater
        infalter.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setActionBar(title: String) {
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.home_menu -> {
                val mIntent = Intent(this, MainActivity::class.java)
                mIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                Toast.makeText(this, resources.getString(R.string.home_menu), Toast.LENGTH_SHORT)
                    .show()
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_AVATAR = "avatar_url"
    }
}