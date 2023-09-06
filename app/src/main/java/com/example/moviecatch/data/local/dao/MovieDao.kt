package com.example.moviecatch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.moviecatch.data.local.entities.MovieData

@Dao
interface MovieDao {
    @Insert
    fun addFavoriteMovie(movie: MovieData)

    @Insert
    fun addWatchlistMovie(movie: MovieData)

    @Insert
    fun addAllMovies(movies: List<MovieData>)

    @Query("SELECT * FROM movies WHERE isFavorite = 1")
    suspend fun readAllFavorites(): List<MovieData>

    @Query("SELECT * FROM movies WHERE isInWatchlist = 1")
    fun readAllWatchlist(): List<MovieData>

    @Query("SELECT * FROM movies")
    suspend fun getAllStoredMovies(): List<MovieData>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovieById(movieId: Int): MovieData?

    @Update
    fun updateMovie(movie: MovieData)

    @Query("DELETE FROM movies WHERE id = :movieId")
    fun deleteMovie(movieId: Int)
}