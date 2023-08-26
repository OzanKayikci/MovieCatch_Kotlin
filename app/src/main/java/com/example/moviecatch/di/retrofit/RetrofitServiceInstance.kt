package com.example.moviecatch.di.retrofit

import com.example.moviecatch.models.Details
import com.example.moviecatch.models.ExternalIds
import com.example.moviecatch.models.Genre
import com.example.moviecatch.models.Movie
import com.example.moviecatch.models.MovieResult
import com.example.moviecatch.models.Trailer
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitServiceInstance {


    @GET("3/movie/popular?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
   suspend fun getPopularVideos(@Query("page") query:String) : Response<Movie>

    @GET("3/movie/now_playing?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    suspend fun getRecentVideos(@Query("page") query:String) : Response<Movie>

    @GET("3/movie/{id}?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    suspend fun getMovieDetails(@Path("id") id:Int) :Response<Details>

    @GET("3/genre/movie/list?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getGenres() : Call<Genre>

    @GET("3/movie/{id}/videos?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    suspend  fun getTrailerTeasers(@Path("id") id:Int): Response<Trailer>

    @GET("3/find/{id}?external_source=imdb_id&api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    suspend fun getMovieById(@Path("id") id:String):Response<MovieResult>
 @GET("3/movie/{id}/external_ids?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
 suspend fun getMovieExternalIds(@Path("id") id:Int):Response<ExternalIds>
    @GET("3/search/movie?api_key=df2885ea32e1b72e3f5c50ef1ec97470")
    fun getSuggestions(@Query("query") query:String) : Call<Movie>

}