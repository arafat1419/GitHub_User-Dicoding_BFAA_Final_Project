package com.dicoding.sub3_githubusers.data.model

import com.google.gson.annotations.SerializedName

data class Repos(
    var name: String?,
    @SerializedName("html_url") var htmlUrl: String?,
    var description: String?,
    @SerializedName("created_at") var createdAt: String?,
    var language: String?,
)