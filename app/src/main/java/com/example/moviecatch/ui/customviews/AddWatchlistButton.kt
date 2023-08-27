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


class AddWatchlistButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : AppCompatImageButton(context, attrs, defStyle) {

    var isWatchlist: Boolean = false
        private set


    fun setWatchList(watchlist: Boolean) {
        isWatchlist = watchlist
        updateView()
    }


    fun toggleWatchlist() {
        isWatchlist = !isWatchlist

        updateView()
    }

    private fun updateView() {
        var drawableId = R.drawable.baseline_playlist_add_24
        alpha = 0.5f
        if (isWatchlist) {
            R.drawable.baseline_playlist_add_active_24
            alpha = 1f
        }

        setImageResource(drawableId)
    }

}