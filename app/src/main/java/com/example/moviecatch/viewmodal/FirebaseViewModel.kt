package com.example.moviecatch.viewmodal

import androidx.lifecycle.ViewModel
import com.example.moviecatch.data.remote.firebase.FirebaseRepository
import com.example.moviecatch.data.remote.firebase.FirebaseResponse
import com.example.moviecatch.data.local.entities.MovieData
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FirebaseViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    fun pushMoviesToFireBase(
        database: FirebaseDatabase,
        movies: List<MovieData>,
        callback: (Boolean, String) -> Unit
    ) {
        val gson = Gson()
        val moviesJson = gson.toJson(movies)
        val moviesRef = database.getReference("FavoriteMovies")
        firebaseRepository.pushToFirebase(moviesRef, moviesJson) { response, message ->
            callback(response, message)

        }
    }

    fun fetchFromFirebase(database: FirebaseDatabase, callback: (FirebaseResponse) -> Unit) {
        val moviesRef = database.getReference("FavoriteMovies")
        firebaseRepository.fetchFromFirebase(moviesRef) { response, data ->
            if (!response) {
                callback(FirebaseResponse.StringResponse(data ?: "Error from cloud"))
                return@fetchFromFirebase
            }
            val gson = Gson()
            val movieDataListType = object : TypeToken<List<MovieData>>() {}.type
            val movieDataList =
                gson.fromJson<List<MovieData>>(data!!, movieDataListType)

            callback(FirebaseResponse.MovieDataResponse(movieDataList))
        }
    }

     fun saveToLocalFromFirebase(
         movies: List<MovieData>,
         existMovies: List<MovieData>,
         callback: (Boolean,MutableList<MovieData>?) -> Unit
    ) {

        val newMovies = mutableListOf<MovieData>()

        for (firebaseMovie in movies) {
            // Check if the Firebase data already exists in Room
            val existingMovie = existMovies.find { it.id == firebaseMovie.id }

            if (existingMovie == null) {
                // Data doesn't exist in Room, so add it to the list of new movies
                newMovies.add(firebaseMovie)
            }
        }

        if (newMovies.isNotEmpty()) {
            callback(true,newMovies)

        } else {
            callback(false,null)

        }

    }
}