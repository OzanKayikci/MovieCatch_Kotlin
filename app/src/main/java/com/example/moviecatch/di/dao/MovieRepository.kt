package com.example.moviecatch.di.dao

import javax.inject.Inject

class MovieRepository @Inject constructor(private val movieDao: MovieDao) {


    suspend fun readMoviesFromDb(forFavorite: Boolean): List<MovieData> {


        return if (forFavorite) movieDao.readAllFavorites() else movieDao.readAllWatchlist()

    }

    fun addMovieToDb(movie: MovieData) {
        movieDao.addFavoriteMovie(movie)
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