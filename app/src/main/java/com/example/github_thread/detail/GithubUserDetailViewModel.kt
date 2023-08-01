package com.example.github_thread.detail

import androidx.lifecycle.ViewModel
import com.example.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GithubUserDetailViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    fun githubUser(username: String) = githubUserUseCase.getGithubUser(username)
    fun githubUserRepo(username: String) = githubUserUseCase.getGithubUserRepos(username)
}