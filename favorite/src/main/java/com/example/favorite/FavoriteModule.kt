package com.example.favorite

import com.example.core.domain.usecase.GithubUserInteractor
import com.example.core.domain.usecase.GithubUserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class FavoriteModule {

    @Binds
    abstract fun provideGithubUserUseCase(githubUserInteractor: GithubUserInteractor): GithubUserUseCase
}