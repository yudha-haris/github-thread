package com.example.core.data.source.local

import com.example.core.data.source.local.entity.GithubUserEntity
import com.example.core.data.source.local.room.GithubUserDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val githubUserDao: GithubUserDao) {
    fun getAllGithubUser(): Flow<List<GithubUserEntity>> = githubUserDao.getAllGithubUsers()

    fun getFavoriteGithubUser(): Flow<List<GithubUserEntity>> = githubUserDao.getFavoriteGithubUsers()

    suspend fun insertGithubUser(githubUserList: List<GithubUserEntity>) = githubUserDao.insertGithubUser(githubUserList)

    fun setFavoriteGithubUser(githubUser: GithubUserEntity, newState: Boolean){
        githubUser.isFavorite = newState
        githubUserDao.updateFavoriteGithubUser(githubUser)
    }
}