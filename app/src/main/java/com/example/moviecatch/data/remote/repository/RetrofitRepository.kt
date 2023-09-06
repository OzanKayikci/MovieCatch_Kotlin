package com.example.moviecatch.data.remote.repository

import androidx.lifecycle.MutableLiveData
import com.example.moviecatch.data.remote.api.RetrofitServiceInstance
import com.example.moviecatch.models.Credits
import com.example.moviecatch.models.Details
import com.example.moviecatch.models.ExternalIds
import com.example.moviecatch.models.Genre
import com.example.moviecatch.models.Movie
import com.example.moviecatch.models.MovieResult
import com.example.moviecatch.models.Trailer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class RetrofitRepository @Inject constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {

    suspend fun getPopularMovies(page: String): Movie? {
        val response: Response<Movie> = retrofitServiceInstance.getPopularVideos(page)

        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    fun getAllGenres(liveData: MutableLiveData<Genre>) {
        retrofitServiceInstance.getGenres().enqueue(object : Callback<Genre> {
            override fun onResponse(call: Call<Genre>, response: Response<Genre>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<Genre>, t: Throwable) {
                liveData.postValue(null)
            }

        })
    }

    suspend fun getRecentMovies(page: String): Movie? {
        val response: Response<Movie> = retrofitServiceInstance.getRecentVideos(page)

        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    suspend fun getMovieDetails(id: Int): Details? {
        val response: Response<Details> = retrofitServiceInstance.getMovieDetails(id)
        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }

    suspend fun getMovieTrailers(id: Int): Trailer? {
        val response: Response<Trailer> = retrofitServiceInstance.getTrailerTeasers(id)
        return if (response.isSuccessful) {
            return response.body()
        } else {
            return null
        }

    }

    suspend fun getMovieCredits(id: Int): Credits? {
        val response: Response<Credits> = retrofitServiceInstance.getMovieCredits(id)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun getMovieExternalIds(id: Int): ExternalIds? {
        val response: Response<ExternalIds> = retrofitServiceInstance.getMovieExternalIds(id)
        return if (response.isSuccessful) {
            return response.body()
        } else {
            null
        }
    }

    suspend fun getMovieById(id: String): MovieResult? {
        val response: Response<MovieResult> = retrofitServiceInstance.getMovieById(id)
        return if (response.isSuccessful) {
            return response.body()

        } else {
            return null
        }
    }

}
