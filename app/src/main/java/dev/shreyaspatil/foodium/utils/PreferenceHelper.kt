package dev.shreyaspatil.foodium.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager


object PreferenceHelper {

    private const val KEY_TOKEN = "dpm_token"

    fun putIntger(key: String?, value: Int, context: Context):PreferenceHelper {
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = preferences.edit()
        edit.putInt(key, value)
        edit.apply()
        return this
    }

    fun getInt(key: String?, context: Context): Int {
        val preferences: SharedPreferences = PreferenceManager
            .getDefaultSharedPreferences(context)
        return preferences.getInt(key, 0)
    }

}