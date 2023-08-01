package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.core.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM github_user where isFavorite = 1")
    fun getFavoriteGithubUsers(): Flow<List<GithubUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithubUser(githubUser: GithubUserEntity)

    @Query("SELECT * FROM github_user where login = :username and isFavorite = 1")
    fun checkFavoriteGithubUser(username: String): Flow<List<GithubUserEntity>>
}