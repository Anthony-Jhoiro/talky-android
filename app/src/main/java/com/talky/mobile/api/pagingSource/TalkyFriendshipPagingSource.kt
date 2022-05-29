package com.talky.mobile.api.pagingSource

import androidx.paging.*
import com.talky.mobile.api.apis.FriendshipControllerApi
import com.talky.mobile.api.models.FriendDto
import retrofit2.HttpException
import java.io.IOException


class TalkyFriendshipPagingSource(private val friendshipControllerApi: FriendshipControllerApi) : PagingSource<Int, FriendDto>() {
    override fun getRefreshKey(state: PagingState<Int, FriendDto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FriendDto> {
        return try {
            val nextPage = params.key ?: 0

            val response = friendshipControllerApi.listFriends(page = nextPage, size = params.loadSize)

            if (!response.isSuccessful) {
                return LoadResult.Error(Exception(response.message()))
            }

            val friendshipList = response.body()
            LoadResult.Page(
                data = friendshipList!!,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (friendshipList.isEmpty()) null else nextPage + 1
            )
        } catch (ex: IOException) {
            return LoadResult.Error(ex)
        } catch (ex: HttpException) {
            return LoadResult.Error(ex)
        }
    }

}
