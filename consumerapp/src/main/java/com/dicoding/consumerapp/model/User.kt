package com.dicoding.consumerapp.model

import com.google.gson.annotations.SerializedName

data class User(
    var login: String? = null,
    var id: Int = 0,
    @SerializedName("avatar_url") var avatarUrl: String? = null,
    @SerializedName("html_url") var htmUrl: String? = null,
    var company: String? = null,
    var blog: String? = null,
    var location: String? = null,
    @SerializedName("public_repos") var repos: Int = 0,
    var followers: Int = 0,
    var following: Int = 0
)