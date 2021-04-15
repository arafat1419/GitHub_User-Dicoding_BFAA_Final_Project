package com.dicoding.consumerapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.consumerapp.adapter.UserAdapter
import com.dicoding.consumerapp.databinding.ActivityMainBinding
import com.dicoding.consumerapp.model.User
import com.dicoding.consumerapp.viewmodel.FavoriteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Toast.makeText(this@MainActivity, "You choose : " + data.login, Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvUser.adapter = adapter
        }

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        viewModel.setFavoriteUser(this)

        viewModel.getFavoriteUser().observe(this, {
            if (it != null) {
                adapter.setList(it)
            }
        })
    }
}