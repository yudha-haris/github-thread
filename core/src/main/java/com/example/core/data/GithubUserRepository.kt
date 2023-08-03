package com.example.core.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.core.data.source.local.LocalDataSource
import com.example.core.data.source.paging.GithubUserPagingSource
import com.example.core.data.source.remote.RemoteDataSource
import com.example.core.data.source.remote.network.ApiResponse
import com.example.core.domain.model.GithubUser
import com.example.core.domain.model.GithubUserRepo
import com.example.core.domain.repsoitory.IGithubUserRepository
import com.example.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
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
                emit(Resource.Success(DataMapper.mapResponseToDomains(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("empty response"))
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

    override suspend fun setFavoriteGithubUser(githubUser: GithubUser, state: Boolean) {
        val githubUserEntity = DataMapper.mapDomainToEntity(githubUser)
        localDataSource.setFavoriteGithubUser(githubUserEntity, state)
    }

    override fun getGithubUser(username: String): Flow<Resource<GithubUser>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGithubUser(username)
        val favorite = localDataSource.checkFavoriteGithubUser(username)
        val isFavorite = favorite.first().isNotEmpty()
        when(val apiResponse = response.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapResponseToDomain(apiResponse.data, isFavorite)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("empty response"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getGithubUserRepos(username: String): Flow<Resource<List<GithubUserRepo>>> = flow {
        emit(Resource.Loading())
        val response = remoteDataSource.getGithubUserRepos(username)
        when (val apiResponse = response.first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(DataMapper.mapReposResponseToDomains(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Error("empty response"))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(apiResponse.errorMessage))
            }
        }
    }
}