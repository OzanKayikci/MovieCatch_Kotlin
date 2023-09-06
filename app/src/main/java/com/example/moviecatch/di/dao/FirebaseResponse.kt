package com.example.moviecatch.di.dao

sealed class FirebaseResponse {
    data class StringResponse(val stringValue: String) : FirebaseResponse()
    data class MovieDataResponse(val movieDataList: List<MovieData>) : FirebaseResponse()
}
