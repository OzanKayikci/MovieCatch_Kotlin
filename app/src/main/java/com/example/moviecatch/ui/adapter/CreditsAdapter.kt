package com.example.moviecatch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviecatch.R
import com.example.moviecatch.models.Cast

import com.example.moviecatch.models.ResultTrailer
import com.example.moviecatch.models.Trailer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.text.SimpleDateFormat
import java.util.Locale

class CreditsAdapter :
    RecyclerView.Adapter<CreditsAdapter.MyCustomHolder>() {

    private var credits: List<Cast>? = null

    fun setList(credits: List<Cast>) {
        this.credits = credits
        notifyDataSetChanged()
    }


    class MyCustomHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val realName = view.findViewById<TextView>(R.id.realName)

        private val roleName = view.findViewById<TextView>(R.id.roleName)
        private val image = view.findViewById<ImageView>(R.id.castImage)


        fun bind(data: Cast) {
            realName.text = data.name
            roleName.text = data.character
            Glide.with(image)
                .load("https://image.tmdb.org/t/p/w342/${data.profile_path}")
                .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCustomHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.details_credit_item, parent, false)
        return MyCustomHolder(view)
    }

    override fun onBindViewHolder(holder: MyCustomHolder, position: Int) {
        val cast = credits!![position]

        holder.bind(cast)
    }

    override fun getItemCount(): Int {
        return if (credits == null) {
            0
        } else {
            return credits!!.size
        }
    }
}