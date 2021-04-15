package com.dicoding.sub3_githubusers.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.sub3_githubusers.api.RetrofitClient
import com.dicoding.sub3_githubusers.data.local.FavoriteUser
import com.dicoding.sub3_githubusers.data.local.FavoriteUserDao
import com.dicoding.sub3_githubusers.data.local.UserDB
import com.dicoding.sub3_githubusers.data.model.Repos
import com.dicoding.sub3_githubusers.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    val userDetail = MutableLiveData<User>()
    val listRepos = MutableLiveData<ArrayList<Repos>>()

    private var userDB: UserDB?
    private var userDao: FavoriteUserDao?

    init {
        userDB = UserDB.getDatabase(application)
        userDao = userDB?.favUser()
    }

    fun setDetailUser(username: String) {
        RetrofitClient.apiInstance
            .getDetailUser(username)
            .enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful)
                        userDetail.postValue(response.body())
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    t.message?.let { Log.d("onFailure Detail : ", it) }
                }
            })
    }

    fun getDetailUser(): LiveData<User> = userDetail

    fun setListRepos(username: String) {
        RetrofitClient.apiInstance
            .getRepos(username)
            .enqueue(object : Callback<ArrayList<Repos>> {
                override fun onResponse(
                    call: Call<ArrayList<Repos>>,
                    response: Response<ArrayList<Repos>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("Success :", response.toString())
                        listRepos.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<Repos>>, t: Throwable) {
                    t.message?.let { Log.d("onFailure", it) }
                }

            })
    }

    fun getListRepos(): LiveData<ArrayList<Repos>> = listRepos

    fun addToFavorite(username: String?, id: Int, avatarUrl: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                username!!,
                id,
                avatarUrl!!
            )
            userDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteUser(id)
        }
    }
}