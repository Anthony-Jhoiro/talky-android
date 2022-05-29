package com.talky.mobile.api

import androidx.paging.*
import com.talky.mobile.api.apis.PostControllerApi
import com.talky.mobile.api.models.PostDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


class TalkyPostsPagingSource(private val postApi: PostControllerApi) : PagingSource<Int, PostDto>() {
    override fun getRefreshKey(state: PagingState<Int, PostDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostDto> {
        return try {
            val nextPage = params.key ?: 0

            val response = postApi.listPosts(page = nextPage, size = params.loadSize)

            if (!response.isSuccessful) {
                return LoadResult.Error(Exception(response.message()))
            }

            val postList = response.body()
            LoadResult.Page(
                data = postList!!,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (postList.isEmpty()) null else nextPage + 1
            )
        } catch (ex: IOException) {
            return LoadResult.Error(ex)
        } catch (ex: HttpException) {
            return LoadResult.Error(ex)
        }
    }

}
