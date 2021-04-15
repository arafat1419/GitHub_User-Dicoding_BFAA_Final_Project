package com.dicoding.consumerapp.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.consumerapp.local.DatabaseContract
import com.dicoding.consumerapp.local.MappingHelper
import com.dicoding.consumerapp.model.User

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private var list = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUser(context: Context) {
        val cursor = context.contentResolver.query(
            DatabaseContract.FavoriteUserColumn.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val listConverted = cursor?.let { MappingHelper.mapCursorToArrayList(it) }
        list.postValue(listConverted)
    }

    fun getFavoriteUser(): LiveData<ArrayList<User>> {
        return list
    }
}