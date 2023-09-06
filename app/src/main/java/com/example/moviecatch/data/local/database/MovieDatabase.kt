package com.example.moviecatch.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviecatch.data.local.dao.MovieDao
import com.example.moviecatch.data.local.entities.MovieData
import com.example.moviecatch.data.local.RoomTypeConverter


@Database(
    entities = [MovieData::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomTypeConverter::class) // Dönüştürücüyü ekliyoruz
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

    companion object {
        private var dbInstance: MovieDatabase? = null

        fun getMovieDB(context: Context): MovieDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                ).allowMainThreadQueries().build()
            }
            return dbInstance!!
        }
    }
}