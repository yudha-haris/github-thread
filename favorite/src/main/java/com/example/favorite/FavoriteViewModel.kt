package com.example.favorite

import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.GithubUserUseCase

class FavoriteViewModel(githubUserUseCase: GithubUserUseCase): ViewModel() {
    val favoriteUsers = githubUserUseCase.getAllFavoriteGithubUser()
}