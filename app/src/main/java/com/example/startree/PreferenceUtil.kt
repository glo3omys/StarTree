package com.example.startree

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)
    fun getSharedPrefs(key: String, value: String): String {
        return prefs.getString(key, value).toString()
    }
    fun setSharedPrefs(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }
}