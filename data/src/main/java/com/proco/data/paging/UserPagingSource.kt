package com.proco.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.proco.data.remote.api.ApiService
import com.proco.domain.fake_data.FakeData
import com.proco.domain.model.user.User

class UserPagingSource(
    private val apiService: ApiService,
    private val search: String? = null,
    private val country: String? = null,
    private val jobTitle: String? = null,

    ) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
//        return try {
//            val currentPage = params.key ?: 1
//            val response = apiService.getUsers(search = search, country = country, jobTitle = jobTitle, page = currentPage)
//            val movies = response ?: emptyList()
//            LoadResult.Page(
//                data = movies,
//                prevKey = if (currentPage == 1) null else currentPage - 1,
//                nextKey = if (movies.isEmpty()) null else currentPage + 1
//            )
//        } catch (exception: IOException) {
//            LoadResult.Error(exception)
//        } catch (exception: HttpException) {
//            LoadResult.Error(exception)
//        }
        return LoadResult.Page(data =  FakeData.users(), prevKey = null, nextKey = 0)
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition
    }
}