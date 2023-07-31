package com.example.core.data.source.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.core.data.source.remote.network.ApiService
import com.example.core.domain.model.GithubUser
import com.example.core.utils.DataMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubUserPagingSource @Inject constructor(private val apiService: ApiService) : PagingSource<Int, GithubUser>(){
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubUser> {
        return try {
            val header = "Token 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"
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getUsers(header, position, 10)

            LoadResult.Page(
                data = DataMapper.mapResponseToDomain(responseData),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (responseData.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}