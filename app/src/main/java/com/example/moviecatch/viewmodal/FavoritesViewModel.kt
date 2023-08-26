package com.example.moviecatch.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviecatch.di.dao.MovieData
import com.example.moviecatch.di.dao.MovieRepository
import com.example.moviecatch.di.retrofit.RetrofitRepository
import com.example.moviecatch.models.Details

import com.example.moviecatch.models.MovieResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val retrofitRepository: RetrofitRepository
) : ViewModel() {


    private var favoriteMovies: MutableLiveData<List<MovieData>> = MutableLiveData()
    private var watchlistMovies: MutableLiveData<List<MovieData>> = MutableLiveData()

    private var changedMovie: MutableLiveData<MovieData> = MutableLiveData()


    suspend fun getFavoriteMoviesFromDb() {
        favoriteMovies.postValue(movieRepository.readMoviesFromDb(true))
    }

    suspend fun getWatchlistMoviesFromDb() {
        watchlistMovies.postValue(movieRepository.readMoviesFromDb(false))
    }

    fun getMovieFromDb(id: Int) {
        changedMovie.postValue(movieRepository.getMovieById(id))
    }

    fun addMovieToDb(movieDetails: Details, isFavorite: Boolean, callback: (Boolean) -> Unit) {


        val existMovie = movieRepository.getMovieById(movieDetails.id)

        var toWatchlist: Boolean = false
        var toFavorite: Boolean = false

        if (existMovie !== null) {
            toWatchlist = existMovie.isInWatchlist
            toFavorite = existMovie.isFavorite
        }

        //if isFavorite false then the function called for watchlist
        val favoritesData = MovieData(
            primary_id = 0,
            id = movieDetails.id,
            posterPath = movieDetails.poster_path,
            title = movieDetails.title,
            releaseDate = movieDetails.release_date,
            voteAverage = movieDetails.vote_average,
            genresId = movieDetails.genres.map { it -> it.id },
            isInWatchlist = if (isFavorite) toWatchlist else false,
            isFavorite = if (isFavorite) true else toFavorite
        )
        viewModelScope.launch {
            try {
                if (existMovie !== null) {
                    movieRepository.updateMovie(favoritesData)
                } else {
                    movieRepository.addMovieToDb(favoritesData)
                }
                callback(true)
            } catch (e: Exception) {
                callback(false)
            }

        }

    }


    fun deleteMovieFromDb(id: Int, callback: (Boolean) -> Unit) {

        try {
            movieRepository.deleteMovie(id)
            callback(true)
        } catch (e: Exception) {
            callback(false)
        }

    }

    /**observable functions*/
    fun getFavoritesObservable(): MutableLiveData<List<MovieData>> {
        return favoriteMovies
    }

    fun getWatchlistObservable(): MutableLiveData<List<MovieData>> {
        return watchlistMovies
    }

    fun getChangedMovieObservable(): MutableLiveData<MovieData> {
        return changedMovie
    }
}