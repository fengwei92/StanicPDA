package com.stanic.pda.util

import android.content.Context
import android.content.SharedPreferences

object SpUtil {
    const val USER_NAME = "userName"
    const val PASSWORD = "password"
    const val PROJECT_CODE = "projectCode"


    private fun getInstance(context: Context): SharedPreferences {
        return context.getSharedPreferences("stanic", Context.MODE_PRIVATE)
    }

    fun put(context: Context, key: String, obj: Any) {
        val edit = getInstance(context).edit()
        when (obj) {
            is String -> edit.putString(key, obj)
            is Int -> edit.putInt(key, obj)
            is Boolean -> edit.putBoolean(key, obj)
        }
        edit.commit()
        edit.apply()
    }

    fun remove(context: Context, key: String) {
        val edit = getInstance(context).edit()
        edit.remove(key)
        edit.apply()
    }

    fun getString(context: Context, key: String): String {
        return getInstance(context).getString(key, "")
    }

    fun getBoolean(context: Context, key: String): Boolean {
        return getInstance(context).getBoolean(key, false)
    }

    fun getInteger(context: Context, key: String): Int {
        return getInstance(context).getInt(key, -1)
    }

}