package com.example.github_thread.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(githubUserUseCase: GithubUserUseCase) : ViewModel() {
    val githubUsers: Flow<PagingData<GithubUser>> = githubUserUseCase.getAllGithubUsers()

}