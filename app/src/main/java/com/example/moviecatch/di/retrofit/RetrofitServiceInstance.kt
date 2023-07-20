package com.example.moviecatch.di.retrofit

import com.example.moviecatch.models.Genre
import com.example.moviecatch.models.Movie
import com.example.moviecatch.models.Trailer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {


    @GET("3/movie/popular?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getPopularVideos(@Query("page") query:String) : Call<Movie>

    @GET("3/movie/now_playing?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getRecentVideos(@Query("page") query:String) : Call<Movie>

    @GET("3/movie/popular?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getGenres() : Call<Genre>

    @GET("3/movie/{id}/videos?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getTrailerTeasers(@Path("id") id:Int): Call<Trailer>

    @GET("3/search/movie?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getSuggestions(@Query("query") query:String) : Call<Movie>
}