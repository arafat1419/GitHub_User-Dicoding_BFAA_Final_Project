package com.dicoding.sub3_githubusers.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.dicoding.sub3_githubusers.data.local.FavoriteUser
import com.dicoding.sub3_githubusers.data.local.FavoriteUserDao
import com.dicoding.sub3_githubusers.data.local.UserDB

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: FavoriteUserDao?
    private var userDB: UserDB?

    init {
        userDB = UserDB.getDatabase(application)
        userDao = userDB?.favUser()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getFavoriteUser()
    }
}