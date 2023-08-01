package com.example.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListGithubUserResponse (
    @field:SerializedName("items")
    val items: List<GithubUserResponse>
    )