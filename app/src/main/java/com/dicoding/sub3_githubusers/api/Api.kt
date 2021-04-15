package com.dicoding.sub3_githubusers.api

import com.dicoding.sub3_githubusers.BuildConfig
import com.dicoding.sub3_githubusers.data.model.Repos
import com.dicoding.sub3_githubusers.data.model.User
import com.dicoding.sub3_githubusers.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<User>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/repos")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun getRepos(
        @Path("username") username: String
    ): Call<ArrayList<Repos>>
}