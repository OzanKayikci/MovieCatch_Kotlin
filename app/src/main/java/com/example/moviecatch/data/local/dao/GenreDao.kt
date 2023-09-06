package com.example.moviecatch.data.local.dao

import androidx.room.Dao
import androidx.room.Insert

import androidx.room.Query
import com.example.moviecatch.data.local.entities.GenreData


@Dao
interface GenreDao {
    @Insert
    fun addGenre(genre: GenreData)

    @Insert
    fun addAllGenres(objects: List<GenreData>)

    @Query("SELECT * FROM genres")
    fun readAllData():List<GenreData>
}