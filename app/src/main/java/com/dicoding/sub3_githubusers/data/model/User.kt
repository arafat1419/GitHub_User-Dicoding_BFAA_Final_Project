package com.dicoding.sub3_githubusers.data.model

import com.google.gson.annotations.SerializedName

data class User(
    var login: String,
    var id: Int = 0,
    @SerializedName("avatar_url") var avatarUrl: String,
    @SerializedName("html_url") var htmlUrl: String? = null,
    var company: String? = null,
    var blog: String? = null,
    var location: String? = null,
    @SerializedName("public_repos") var repos: Int = 0,
    var followers: Int = 0,
    var following: Int = 0
)