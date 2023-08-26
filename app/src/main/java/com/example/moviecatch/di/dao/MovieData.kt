package com.example.moviecatch.di.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieData(
    @PrimaryKey(autoGenerate = true)
    val primary_id: Int,
    val id: Int,
    val title: String,
    val genresId: List<Int>,
    val posterPath: String,
    val releaseDate:String,
    val voteAverage:Double,
    val isFavorite:Boolean,
    val isInWatchlist:Boolean
)