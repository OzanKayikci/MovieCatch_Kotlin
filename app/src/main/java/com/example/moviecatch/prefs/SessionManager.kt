package com.example.moviecatch.prefs

import android.content.SharedPreferences
import com.example.moviecatch.util.Constants
import javax.inject.Inject

class SessionManager @Inject constructor(private val preferences :SharedPreferences) {

    var theme: Boolean
        get() = preferences.getBoolean(Constants.THEME_KEY, false)
        set(value) = preferences.edit().putBoolean(Constants.THEME_KEY, value).apply()

    var isFirstRun: Boolean
        get() = preferences.getBoolean(Constants.FIRST_RUN_KEY, true)
        set(value) = preferences.edit().putBoolean(Constants.FIRST_RUN_KEY, value).apply()

/**Get ve set olan fonksiyonları property olarak yaz*/
//    fun getTheme() = preferences.getBoolean(Constants.THEM_KEY, false)
//
//    fun setTheme(value: Boolean){
//        val editor = preferences.edit()
//        editor.putBoolean(Constants.THEM_KEY,value)
//        editor.apply()
//    }
//
//    fun getIsFirstRun() = preferences.getBoolean(Constants.FIRST_RUN_KEY,false)
//
//    fun setIsFirstRun(value: Boolean){
//        val editor = preferences.edit()
//        editor.putBoolean(Constants.FIRST_RUN_KEY,value)
//    }
}