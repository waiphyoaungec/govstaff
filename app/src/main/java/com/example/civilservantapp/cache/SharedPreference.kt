package com.example.civilservantapp.cache

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(val context: Context) {
    private val PREFS_NAME = "civilservant"
    val sharedPreference: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserInfo(KEY_NAME: String, text: String){
        val editor: SharedPreferences.Editor = sharedPreference.edit()
        editor.putString(KEY_NAME, text)
        editor!!.commit()
    }

    fun getUserInfo(KEY_NAME: String): String?{
        return sharedPreference.getString(KEY_NAME, null)
    }
}