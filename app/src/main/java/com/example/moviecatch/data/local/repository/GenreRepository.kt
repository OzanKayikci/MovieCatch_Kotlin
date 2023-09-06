package com.example.moviecatch.data.local.repository

import com.example.moviecatch.data.local.dao.GenreDao
import com.example.moviecatch.data.local.entities.GenreData
import javax.inject.Inject

class GenreRepository @Inject constructor(private val genreDao: GenreDao) {

    val readAllData:List<GenreData> = genreDao.readAllData()

    fun addGenre(genreData: GenreData){
        genreDao.addGenre(genreData)
    }

    fun addAllGenres(genreList:List<GenreData>){
        genreDao.addAllGenres(genreList)
    }

}