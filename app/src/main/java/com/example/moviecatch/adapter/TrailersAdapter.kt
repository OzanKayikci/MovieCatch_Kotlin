package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatch.R

import com.example.moviecatch.models.ResultTrailer
import com.example.moviecatch.models.Trailer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.SimpleDateFormat
import java.util.Locale

class TrailersAdapter :
    RecyclerView.Adapter<TrailersAdapter.MyCustomHolder>() {

    var trailers: List<ResultTrailer>? = null

    fun setList(trailers: List<ResultTrailer>) {
        this.trailers = trailers
        notifyDataSetChanged()
    }


    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val videoTitle = view.findViewById<TextView>(R.id.videoTitle)
        private val youtubePlayer = view.findViewById<YouTubePlayerView>(R.id.youtube_player_view)
        private val date = view.findViewById<TextView>(R.id.releaseDate)
        private val type = view.findViewById<TextView>(R.id.videoType)

        private val youTubePlayerView: YouTubePlayerView = youtubePlayer
        private var initialized = false

        private fun initTrailerVideo(id: String) {


            val iFramePlayerOptions = IFramePlayerOptions.Builder()
                .controls(1)
                .autoplay(0)
                .mute(1)
                .fullscreen(0) // enable full screen button
                .build()



            youTubePlayerView.initialize(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(id, 0f)

//                val enterFullscreenButton = findViewById<Button>(R.id.enter_fullscreen_button)
//                enterFullscreenButton.setOnClickListener {
//                    youTubePlayer.toggleFullscreen()
//                }
                }
            }, iFramePlayerOptions)

        }

        private fun convertDate(inputDate: String): String {

            val outputDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

            val parsedDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                .parse(inputDate)

            return outputDateFormat.format(parsedDate)
        }

        fun bind(data: ResultTrailer) {
            videoTitle.text = data.name
            date.text = convertDate(data.published_at)
            type.text = data.type
            if (!initialized) {
                initTrailerVideo(data.key)
                initialized = true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_trailer_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        val trailer = trailers!![position]

        holder.bind(trailer)
    }

    override fun getItemCount(): Int {
        return if (trailers == null) {
            0
        } else {
            return trailers!!.size
        }
    }
}