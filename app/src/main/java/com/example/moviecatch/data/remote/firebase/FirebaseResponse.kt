package com.example.moviecatch.data.remote.firebase

import com.example.moviecatch.data.local.entities.MovieData

sealed class FirebaseResponse {
    data class StringResponse(val stringValue: String) : FirebaseResponse()
    data class MovieDataResponse(val movieDataList: List<MovieData>) : FirebaseResponse()
}
