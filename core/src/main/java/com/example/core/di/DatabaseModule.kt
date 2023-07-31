package com.example.core.di

import android.content.Context
import androidx.room.Room
import com.example.core.data.source.local.room.GithubUserDao
import com.example.core.data.source.local.room.GithubUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubUserDatabase = Room.databaseBuilder(
        context,
        GithubUserDatabase::class.java, "GithubUser.db"
    ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideGithubUserDao(database: GithubUserDatabase): GithubUserDao = database.githubUserDao()
}