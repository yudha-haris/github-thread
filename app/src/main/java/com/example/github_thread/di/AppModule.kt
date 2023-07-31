package com.example.github_thread.di

import com.example.core.domain.usecase.GithubUserInteractor
import com.example.core.domain.usecase.GithubUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun provideGithubUserUseCase(githubUserInteractor: GithubUserInteractor): GithubUserUseCase
}