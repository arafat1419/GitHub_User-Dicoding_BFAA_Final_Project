package com.dicoding.sub3_githubusers.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteUser::class],
    version = 1,
)

abstract class UserDB : RoomDatabase() {

    abstract fun favUser(): FavoriteUserDao

    companion object {
        private const val databaseName = "database_user"
        private var INSTANCE: UserDB? = null

        fun getDatabase(context: Context): UserDB? {
            if (INSTANCE == null) {
                synchronized(UserDB::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDB::class.java,
                        databaseName
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}