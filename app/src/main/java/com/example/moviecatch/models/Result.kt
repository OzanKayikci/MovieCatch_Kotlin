package com.example.moviecatch.models

data class Result(
    val backdrop_page:String,
    val genre_ids : List<Int>,
    val id :Int,
    val overview: String,
    val popularity: Double,
    val poster_path:String,
    val release_date:String,
    val title:String,
    val vote_average:Double,
    val genreStringTr:String,
    val genreString:String
)
