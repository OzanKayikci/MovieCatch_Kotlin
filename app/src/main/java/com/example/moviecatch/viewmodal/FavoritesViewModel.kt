package com.example.moviecatch.viewmodal

import android.util.Log
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
    private var allMovies: MutableLiveData<List<MovieData>> = MutableLiveData()

    private var changedMovie: MutableLiveData<MovieData> = MutableLiveData()


    suspend fun getFavoriteMoviesFromDb() {
        favoriteMovies.postValue(movieRepository.readMoviesFromDb(true))
    }

    suspend fun getWatchlistMoviesFromDb() {
        watchlistMovies.postValue(movieRepository.readMoviesFromDb(false))
    }

    suspend fun getAllMoviesFromDb() {
        allMovies.postValue(movieRepository.getAllStoredMovies())
    }

    fun getMovieFromDb(id: Int) {
        changedMovie.postValue(movieRepository.getMovieById(id))
    }

    private fun getMovieFromDbPrivate(id: Int): MovieData? {
        return movieRepository.getMovieById(id)
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
            primary_id = if (existMovie !== null) existMovie.primary_id else 0,
            id = movieDetails.id,
            posterPath = movieDetails.poster_path,
            title = movieDetails.title,
            releaseDate = movieDetails.release_date,
            voteAverage = movieDetails.vote_average,
            genresId = movieDetails.genres.map { it -> it.id },
            isInWatchlist = if (isFavorite) toWatchlist else true,
            isFavorite = if (isFavorite) true else toFavorite
        )

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

    fun addAllMovieToDb(movies: List<MovieData>, callback: (Boolean) -> Unit) {
        try {
            movieRepository.addALlMoviesToDb(movies)
            callback(true)
        } catch (e: Exception) {
            Log.d("room error", e.message.toString())
            callback(false)
        }
    }

    fun deleteMovieFromDb(id: Int, fromFavorite: Boolean, callback: (Boolean) -> Unit) {

        try {


            if (getMovieFromDbPrivate(id)!!.isFavorite && getMovieFromDbPrivate(id)!!.isInWatchlist) {
                var updatedMovie: MovieData = if (fromFavorite) {
                    getMovieFromDbPrivate(id)!!.copy(isFavorite = false)

                } else {
                    getMovieFromDbPrivate(id)!!.copy(isInWatchlist = false)

                }
                movieRepository.updateMovie(updatedMovie)

            } else {
                movieRepository.deleteMovie(id)
            }

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

    fun getAllStoredMoviesObserve(): MutableLiveData<List<MovieData>> {
        return allMovies
    }

    fun getChangedMovieObservable(): MutableLiveData<MovieData> {
        return changedMovie
    }
}