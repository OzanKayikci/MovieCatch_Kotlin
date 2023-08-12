package com.example.moviecatch.viewmodal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviecatch.di.retrofit.RetrofitRepository
import com.example.moviecatch.models.Genre
import com.example.moviecatch.models.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {
    var populerMovieList: MutableLiveData<Movie>
    var recentMovieList: MutableLiveData<Movie>
    var genreList: MutableLiveData<Genre>

    init {
        populerMovieList = MutableLiveData()
        recentMovieList = MutableLiveData()
        genreList = MutableLiveData()
    }

    fun getObserverGenre(): MutableLiveData<Genre> {
        return genreList
    }

    fun getObserverLiveData(isPopular: Boolean): MutableLiveData<Movie> {
        return if (isPopular) populerMovieList else recentMovieList
    }

    fun loadGenreData() {
        repository.getAllGenres(genreList)


    }

   suspend fun loadData(page: String, isPopular: Boolean) {
        if (isPopular) {
            populerMovieList.postValue(
                repository.getPopularMovies(
                    page,
                )
            )
        } else recentMovieList.postValue(repository.getRecentMovies(page))
    }


}