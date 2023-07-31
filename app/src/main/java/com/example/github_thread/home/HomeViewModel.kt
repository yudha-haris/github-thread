package com.example.github_thread.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    val githubUsers: Flow<PagingData<GithubUser>> = githubUserUseCase.getAllGithubUsers()
    fun searchUsers(username: String): LiveData<Resource<List<GithubUser>>> {
        _searchResults.value = Resource.Loading()
        return githubUserUseCase.getSearchGithubUsers(username).asLiveData()
    }

    private val _searchResults: MutableStateFlow<Resource<List<GithubUser>>> = MutableStateFlow(Resource.Error("init"))
    val searchResults: StateFlow<Resource<List<GithubUser>>>
        get() = _searchResults
    fun inputResult(users: List<GithubUser>){
        _searchResults.value = Resource.Success(users)
    }

    fun clearSearch(){
        _searchResults.value = Resource.Error("clear")
    }

}