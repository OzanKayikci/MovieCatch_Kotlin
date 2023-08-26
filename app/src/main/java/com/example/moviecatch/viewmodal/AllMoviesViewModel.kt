package com.example.moviecatch.viewmodal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviecatch.di.retrofit.RetrofitRepository
import com.example.moviecatch.models.MovieResult
import com.example.moviecatch.paging.MoviesPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllMoviesViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    val loading =  MutableLiveData<Boolean>()

    fun getMoviesByPage(page: Int, type: String): Flow<PagingData<MovieResult>> {

        return Pager(
            config = PagingConfig(pageSize = page),
            pagingSourceFactory = { MoviesPagingSource(repository,type) }
        ).flow.cachedIn(viewModelScope)
    }
}