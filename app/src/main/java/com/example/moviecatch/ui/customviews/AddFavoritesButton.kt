package com.example.moviecatch.ui.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.moviecatch.R
import com.example.moviecatch.models.ExternalIds
import com.example.moviecatch.models.MovieResult


class AddFavoritesButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : AppCompatImageButton(context, attrs, defStyle) {

    var isFavorite: Boolean = false
        private set

    private var movieResult: MovieResult? = null

    private val onMovieClick: ((MovieResult, Boolean) -> Boolean)? = null


    fun setFavorite(favorite: Boolean) {
        isFavorite = favorite
        updateView()
    }

    fun bindMovie(movie: MovieResult) {
        movieResult = movie
        // Diğer bağlama işlemleri

    }

    fun toggleFavorite() {
        isFavorite = !isFavorite

        updateView()
    }

    private fun updateView() {
        var drawableId = R.drawable.baseline_favorite_24
        alpha = 0.5f
        if (isFavorite) {
            R.drawable.baseline_favorite_active_24
            alpha = 1f
        }

        setImageResource(drawableId)
//        if (isFavorite) {
//            setColorFilter(
//                ContextCompat.getColor(
//                    context,
//                    R.color.primaryActiveColor
//                )
//            )
//        } else {
//            setColorFilter(
//                ContextCompat.getColor(
//                    context,
//                    R.color.primaryColor
//                )
//            )
//
//
//        }
    }

}