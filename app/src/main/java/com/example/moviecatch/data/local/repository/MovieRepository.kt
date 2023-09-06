package com.example.moviecatch.data.local.repository

import com.example.moviecatch.data.local.dao.MovieDao
import com.example.moviecatch.data.local.entities.MovieData
import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao: MovieDao) {


    suspend fun readMoviesFromDb(forFavorite: Boolean): List<MovieData> {


        return if (forFavorite) movieDao.readAllFavorites() else movieDao.readAllWatchlist()

    }

    suspend fun getAllStoredMovies(): List<MovieData> {
        return movieDao.getAllStoredMovies()
    }

    fun addMovieToDb(movie: MovieData) {
        movieDao.addFavoriteMovie(movie)
    }

    fun addALlMoviesToDb(movies: List<MovieData>) {
        movieDao.addAllMovies(movies)
    }

    fun getMovieById(id: Int): MovieData? {
        return movieDao.getMovieById(id)
    }

    fun updateMovie(movie: MovieData) {
        return movieDao.updateMovie(movie)
    }

    fun deleteMovie(id: Int) {
        movieDao.deleteMovie(id)
    }
}