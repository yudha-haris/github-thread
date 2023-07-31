package com.example.core.data.source.remote

import android.util.Log
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.data.source.remote.network.ApiService
import com.example.core.data.source.remote.response.GithubUserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun searchGithubUsers(username: String): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow {
            try {
                val response = apiService.searchUser(username)
                if(response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e :Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGithubUser(username: String): Flow<ApiResponse<GithubUserResponse>> {
        return flow {
            try {
                val response = apiService.getUser(username)
                emit(ApiResponse.Success(response))
            } catch (e :Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGithubUserFollowers(username: String): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                if(response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e :Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getGithubUserFollowing(username: String): Flow<ApiResponse<List<GithubUserResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowing(username)
                if(response.isNotEmpty()){
                    emit(ApiResponse.Success(response))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e :Exception){
                emit(ApiResponse.Error(e.message.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}