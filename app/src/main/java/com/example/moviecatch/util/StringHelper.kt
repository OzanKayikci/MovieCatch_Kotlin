package com.example.moviecatch.util

class StringHelper {
    fun getTrName(name: String): String {
        var tr_name = ""

        when (name) {
            "Action" -> {
                tr_name = "Aksiyon"
            }
            "Adventure" -> {
                tr_name = "Macera"
            }
            "Animation" -> {
                tr_name = "Animasyon"
            }
            "Comedy" -> {
                tr_name = "Komedi"
            }
            "Crime" -> {
                tr_name = "Suç"
            }
            "Documentary" -> {
                tr_name = "Belgesel"
            }
            "Drama" -> {
                tr_name = "Drama"
            }
            "Family" -> {
                tr_name = "Aile"
            }
            "Fantasy" -> {
                tr_name = "Fantazi"
            }
            "History" -> {
                tr_name = "History"
            }
            "Horror" -> {
                tr_name = "Korku"
            }
            "Music" -> {
                tr_name = "Müzik"
            }
            "Mystery" -> {
                tr_name = "Gizem"
            }
            "Romance" -> {
                tr_name = "Romantik"
            }
            "Science Fiction" -> {
                tr_name = "Bilim Kurgu"
            }
            "TV Movie" -> {
                tr_name = "TV Filim"
            }
            "Thriller" -> {
                tr_name = "Gerilim"
            }
            "War" -> {
                tr_name = "Savaş"
            }
            "Western" -> {
                tr_name = "Batı"
            }
        }

        return tr_name

    }
}