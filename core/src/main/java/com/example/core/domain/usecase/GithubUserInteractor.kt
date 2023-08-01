package com.example.core.domain.usecase

import androidx.paging.PagingData
import com.example.core.data.GithubUserRepository
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.core.domain.model.GithubUserRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubUserInteractor @Inject constructor(private val githubUserRepository: GithubUserRepository) : GithubUserUseCase {
    override fun getAllGithubUsers(): Flow<PagingData<GithubUser>> = githubUserRepository.getAllGithubUsers()
    override fun getSearchGithubUsers(username: String): Flow<Resource<List<GithubUser>>> = githubUserRepository.searchGithubUsers(username)
    override fun getGithubUser(username: String): Flow<Resource<GithubUser>> = githubUserRepository.getGithubUser(username)
    override fun getGithubUserRepos(username: String): Flow<Resource<List<GithubUserRepo>>> = githubUserRepository.getGithubUserRepos(username)
    override suspend fun setFavoriteGithubUser(githubUser: GithubUser, newState: Boolean) = githubUserRepository.setFavoriteGithubUser(githubUser, newState)

    override fun getAllFavoriteGithubUser(): Flow<List<GithubUser>> = githubUserRepository.getFavoriteGithubUser()
}