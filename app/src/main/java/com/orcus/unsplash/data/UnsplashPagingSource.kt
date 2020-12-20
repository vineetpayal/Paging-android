package com.orcus.unsplash.data

import android.util.Log
import androidx.paging.PagingSource
import com.orcus.unsplash.api.UnsplashApi
import retrofit2.HttpException
import java.io.IOException

private const val UNSPLASH_STARTING_PAGE_INDEX = 1
private const val TAG = "pagingDataSource"

class UnsplashPagingSource(

    private val unsplashApi: UnsplashApi,
    private val query: String

) : PagingSource<Int, UnsplashPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashPhoto> {
        val currentPosition = params.key ?: UNSPLASH_STARTING_PAGE_INDEX

        return try {


            val response = unsplashApi.searchPhotos(query, currentPosition, params.loadSize)

            val photos = response.results

            LoadResult.Page(
                data = photos,
                prevKey = if (currentPosition == UNSPLASH_STARTING_PAGE_INDEX) null else currentPosition - 1,
                nextKey = if (photos.isEmpty()) null else currentPosition + 1
            )
        } catch (e: IOException) {
            Log.d(TAG, "load: ${e.message}")
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.d(TAG, "load: ${e.message}")
            LoadResult.Error(e)
        }

    }
}