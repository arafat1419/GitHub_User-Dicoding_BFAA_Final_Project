package com.dicoding.consumerapp.local

import android.database.Cursor
import com.dicoding.consumerapp.model.User

object MappingHelper {
    fun mapCursorToArrayList(cursor: Cursor?): ArrayList<User> {
        val list = ArrayList<User>()
        cursor?.apply {
            while (cursor.moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.ID))
                val username =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.USERNAME))
                val avatarUrl =
                    getString(getColumnIndexOrThrow(DatabaseContract.FavoriteUserColumn.AVATAR_URL))
                list.add(
                    User(
                        username,
                        id,
                        avatarUrl
                    )
                )
            }
        }
        return list
    }
}