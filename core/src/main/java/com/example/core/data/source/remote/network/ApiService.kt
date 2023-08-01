package com.example.core.data.source.remote.network

import com.example.core.data.source.remote.response.GithubUserReposResponse
import com.example.core.data.source.remote.response.GithubUserResponse
import com.example.core.data.source.remote.response.SearchGithubUserResponse
import retrofit2.http.*

interface ApiService {
    @GET("users")
    suspend fun getUsers(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int,
    ): List<GithubUserResponse>

    @GET("search/users")
    suspend fun searchUser(
        @Query("q") username: String
    ): SearchGithubUserResponse

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): GithubUserResponse

    @GET("users/{username}/repos")
    suspend fun getRepos(
        @Path("username") username: String
    ): List<GithubUserReposResponse>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): List<GithubUserResponse>
}