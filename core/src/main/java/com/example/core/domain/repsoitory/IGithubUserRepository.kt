package com.example.core.domain.repsoitory

import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface IGithubUserRepository {
    fun getAllGithubUsers(): Flow<PagingData<GithubUser>>
    fun searchGithubUsers(username: String): Flow<Resource<List<GithubUser>>>
    fun getFavoriteGithubUser(): Flow<List<GithubUser>>
    fun setFavoriteGithubUser(githubUser: GithubUser, state: Boolean)
}