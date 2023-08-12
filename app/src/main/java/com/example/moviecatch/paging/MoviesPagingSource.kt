package com.example.moviecatch.paging

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviecatch.di.retrofit.RetrofitRepository
import com.example.moviecatch.models.Movie
import com.example.moviecatch.models.Result
import java.lang.Exception

class MoviesPagingSource(private val repository: RetrofitRepository,private  val movieType:String) : PagingSource<Int, Result>() {

    //TODO: ERROR -> when scrolling down after multiple times there is an error about "StringIndexOutOfBoundsException" -->java.lang.StringIndexOutOfBoundsException: begin 0, end -3, length 0
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val currentPage = params.key ?: 1

            val response =
                if (movieType == "Popular") repository.getPopularMovies(
                    currentPage.toString(),

                    ) else repository.getRecentMovies(
                    currentPage.toString()
                )


            LoadResult.Page(
                data = response!!.results,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception){
           LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        TODO("Not yet implemented")
    }


}