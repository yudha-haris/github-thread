package com.example.github_thread.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.core.domain.usecase.GithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private var _githubUsers: LiveData<Resource<List<GithubUser>>> =
        MutableLiveData<Resource<List<GithubUser>>>()

    fun githubUsers() = _githubUsers;

    fun searchGithubUser(username: String){
        _githubUsers = githubUserUseCase.getSearchGithubUsers(username).asLiveData();
    }

    fun getGithubUsers(){
        _githubUsers = githubUserUseCase.getAllGithubUsers().asLiveData();
    }

}