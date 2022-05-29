package com.talky.mobile.api

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.talky.mobile.api.apis.PostControllerApi
import com.talky.mobile.api.models.PostDto
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

class TalkyUserPostsRemoteSource constructor(private val postApi: PostControllerApi, private val uuid: UUID) : PagingSource<Int, PostDto>() {

    override fun getRefreshKey(state: PagingState<Int, PostDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostDto> {
        return try {
            val nextPage = params.key ?: 0

            val response = postApi.listUserPosts(uuid, page = nextPage, size = params.loadSize)

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