package com.example.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.core.data.source.local.entity.GithubUserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubUserDao {

    @Query("SELECT * FROM github_user")
    fun getAllGithubUsers(): Flow<List<GithubUserEntity>>

    @Query("SELECT * FROM github_user where isFavorite = 1")
    fun getFavoriteGithubUsers(): Flow<List<GithubUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGithubUser(githubUser: List<GithubUserEntity>)

    @Update
    fun updateFavoriteGithubUser(githubUser: GithubUserEntity)
}