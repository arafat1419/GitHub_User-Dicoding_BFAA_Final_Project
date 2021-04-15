package com.dicoding.sub3_githubusers.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.sub3_githubusers.R
import com.dicoding.sub3_githubusers.data.local.FavoriteUser
import com.dicoding.sub3_githubusers.data.model.User
import com.dicoding.sub3_githubusers.databinding.FragmentFavoritBinding
import com.dicoding.sub3_githubusers.ui.activity.DetailActivity
import com.dicoding.sub3_githubusers.ui.adapter.UserAdapter
import com.dicoding.sub3_githubusers.ui.viewmodel.FavoriteViewModel

class FavoritFragment : Fragment() {

    private var _binding: FragmentFavoritBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

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

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = GridLayoutManager(context, 2)
            rvUser.adapter = adapter
        }
        viewModel.getFavoriteUser()?.observe(viewLifecycleOwner, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.login,
                user.id,
                user.avatarUrl
            )
            listUsers.add(userMapped)
        }

        return listUsers
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}