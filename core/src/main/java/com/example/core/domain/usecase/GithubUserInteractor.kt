package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.data.GithubUserRepository
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubUserInteractor @Inject constructor(private val githubUserRepository: GithubUserRepository) : GithubUserUseCase {
    override fun getAllGithubUsers(): Flow<PagingData<GithubUser>> = githubUserRepository.getAllGithubUsers()
    override fun getSearchGithubUsers(username: String): Flow<Resource<List<GithubUser>>> = githubUserRepository.searchGithubUsers(username)
}