package com.example.moviecatch.viewmodal

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatch.di.retrofit.RetrofitRepository
import com.example.moviecatch.models.Details
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val repository: RetrofitRepository) :
    ViewModel() {

    var details: MutableLiveData<Details>

    init {
        details = MutableLiveData()
    }

    fun getObservableMovieDetails(): MutableLiveData<Details> {
        return details
    }

    suspend fun getMovieDetails(id: Int) {
        details.postValue(
            repository.getMovieDetails(id)
        )
    }
}