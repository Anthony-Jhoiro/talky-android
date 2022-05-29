package com.talky.mobile.api.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.auth.User
import com.talky.mobile.api.apis.PostControllerApi
import com.talky.mobile.api.apis.UserControllerApi
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.api.models.UserDto
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

class TalkyUserListRemoteSource(private val userApi: UserControllerApi) : PagingSource<Int, UserDto>() {
    override fun getRefreshKey(state: PagingState<Int, UserDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserDto> {
        return try {
            val nextPage = params.key ?: 0

            val response = userApi.getUsers(page = nextPage, size = params.loadSize)

            if (!response.isSuccessful) {
                return LoadResult.Error(Exception(response.message()))
            }

            val userList = response.body()
            LoadResult.Page(
                data = userList!!,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (userList.isEmpty()) null else nextPage + 1
            )
        } catch (ex: IOException) {
            return LoadResult.Error(ex)
        } catch (ex: HttpException) {
            return LoadResult.Error(ex)
        }
    }

}