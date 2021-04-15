package com.dicoding.sub3_githubusers.ui.fragment

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.sub3_githubusers.R
import com.dicoding.sub3_githubusers.data.model.User
import com.dicoding.sub3_githubusers.databinding.FragmentHomeBinding
import com.dicoding.sub3_githubusers.ui.activity.DetailActivity
import com.dicoding.sub3_githubusers.ui.adapter.UserAdapter
import com.dicoding.sub3_githubusers.ui.viewmodel.MainViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                Intent(activity, DetailActivity::class.java).also {
                    Toast.makeText(
                        context,
                        resources.getString(R.string.choose, data.login),
                        Toast.LENGTH_SHORT
                    ).show()
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, data.avatarUrl)
                    startActivity(it)
                }
            }
        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        viewModel.getSearchUser().observe(viewLifecycleOwner, {
            Log.d("Data Users", it.toString())
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })

        binding.apply {
            rvUsers.layoutManager = GridLayoutManager(activity, 2)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUser()
                showLoading(true)
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }

            private fun searchUser() {
                binding.apply {
                    val query = searchView.query.toString()
                    if (query.isEmpty()) return
                    viewModel.setSearchUser(query)
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.welcomeCard.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.welcomeCard.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}