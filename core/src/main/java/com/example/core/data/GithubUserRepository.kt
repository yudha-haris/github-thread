package com.example.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.paging.GithubUserPagingSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.GithubUser
import com.example.core.domain.repsoitory.IGithubUserRepository
import com.example.core.utils.AppExecutors
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
    private val githubUserPagingSource: GithubUserPagingSource
) : IGithubUserRepository {
    override fun getAllGithubUsers(): Flow<PagingData<GithubUser>> =
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                githubUserPagingSource
            }
        ).flow

    override fun searchGithubUsers(username: String): Flow<Resource<List<GithubUser>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.searchGithubUsers(username)
        when (val apiResponse = response.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapResponseToDomain(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("empty respone"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getFavoriteGithubUser(): Flow<List<GithubUser>> {
        return localDataSource.getFavoriteGithubUser().map {
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteGithubUser(githubUser: GithubUser, state: Boolean) {
        val githubUserEntity = DataMapper.mapDomainToEntity(githubUser)
        appExecutors.diskIO().execute {
            localDataSource.setFavoriteGithubUser(githubUserEntity, state)
        }
    }
}