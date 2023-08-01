package com.example.github_thread.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.GithubUser
import com.example.core.domain.usecase.GithubUserUseCase
import com.example.core.utils.AppExecutors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubUserDetailViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    fun githubUser(username: String) = githubUserUseCase.getGithubUser(username)
    fun githubUserRepo(username: String) = githubUserUseCase.getGithubUserRepos(username)

    fun setFavoriteUser(user: GithubUser, newState: Boolean) {
        viewModelScope.launch {
            githubUserUseCase.setFavoriteGithubUser(user, newState)
        }
    }
}