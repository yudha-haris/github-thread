package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.GithubUserResponse
import com.example.core.data.source.remote.response.ListGithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUsers(): ListGithubUserResponse

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): ListGithubUserResponse

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ): GithubUserResponse

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): List<GithubUserResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<GithubUserResponse>
}