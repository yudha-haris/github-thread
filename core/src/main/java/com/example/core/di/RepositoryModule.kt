package com.example.core.di

import com.example.core.data.GithubUserRepository
import com.example.core.domain.repsoitory.IGithubUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(githubUserRepository: GithubUserRepository): IGithubUserRepository
}