package com.example.github_thread.di

import com.example.core.domain.usecase.GithubUserUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun githubUserUseCase(): GithubUserUseCase
}