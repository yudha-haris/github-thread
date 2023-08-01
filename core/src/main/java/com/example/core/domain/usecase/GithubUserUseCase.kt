package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.core.domain.model.GithubUserRepo
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    fun getAllGithubUsers(): Flow<PagingData<GithubUser>>
    fun getSearchGithubUsers(username: String): Flow<Resource<List<GithubUser>>>
    fun getGithubUser(username: String): Flow<Resource<GithubUser>>

    fun getGithubUserRepos(username: String): Flow<Resource<List<GithubUserRepo>>>
}