package com.example.moviecatch.viewmodal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatch.data.remote.repository.RetrofitRepository
import com.example.moviecatch.models.Credits
import com.example.moviecatch.models.Details
import com.example.moviecatch.models.Trailer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    private var details: MutableLiveData<Details> = MutableLiveData()
    private var trailers: MutableLiveData<Trailer> = MutableLiveData()
    private var credits: MutableLiveData<Credits> = MutableLiveData()

    fun getObservableMovieDetails(): MutableLiveData<Details> {
        return details
    }

    fun getObservableMovieTrailers(): MutableLiveData<Trailer> {
        return trailers
    }

    fun getObservableMovieCredit(): MutableLiveData<Credits> {
        return credits
    }

    suspend fun getMovieDetails(id: Int) {
        details.postValue(
            repository.getMovieDetails(id)
        )
    }

    suspend fun getMovieTrailers(id: Int) {
        trailers.postValue(
            repository.getMovieTrailers(id)
        )
    }

    suspend fun getMovieCredits(id: Int) {
        credits.postValue(repository.getMovieCredits(id))
    }
}