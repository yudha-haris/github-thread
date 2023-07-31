package com.example.core.domain.usecase

import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {
    fun getAllGithubUsers(): Flow<Resource<List<GithubUser>>>
    fun getSearchGithubUsers(username: String): Flow<Resource<List<GithubUser>>>
}