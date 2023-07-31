package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.GithubUserResponse
import retrofit2.http.*

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") token: String,
        @Query("since") since: Int,
        @Query("per_page") perPage: Int,
    ): List<GithubUserResponse>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): List<GithubUserResponse>

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