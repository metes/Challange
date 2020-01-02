package com.base.commons

import android.content.Context
import android.content.SharedPreferences
import com.base.commons.Constants.favorites
import com.base.model.local.SongListWraper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class SharedPrefHelper(private val context: Context) {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("reasonkey", Context.MODE_PRIVATE)
    }

    fun loadFavorites(): List<SongListWraper> {
        val type = object : TypeToken<List<SongListWraper>>() {}
        return retrieveListData(favorites, type)?: listOf()
    }

    fun saveFavorites(newList: List<SongListWraper>) {
        addListData(favorites, newList)
    }

//    private fun <T> saveList(reasonkey: String, `object`: List<T>) {
//        val mPrefs = context.getSharedPreferences(reasonkey, Context.MODE_PRIVATE)
//        val prefsEditor = mPrefs.edit()
//        val json = Gson().toJson(`object`)
//        prefsEditor.putString("myJson", json)
//        prefsEditor.apply()
//    }
//
//    private fun <T> loadList(reasonKey: String): List<T> {
//        val mPrefs = context.getSharedPreferences(reasonKey, Context.MODE_PRIVATE)
//        val json = mPrefs.getString("myJson", "")
//        return if (json.isNullOrEmpty()) {
//            ArrayList()
//        } else {
//            try {
//                val type = object : TypeToken<List<T>>() {  }.type
//                Gson().fromJson<List<T>>(json, type)
//            } catch (jsonException: JsonSyntaxException) {
//                // Model formati degisti ise
//                @SuppressLint("CommitPrefEdits")
//                val editor = context.getSharedPreferences(reasonKey, Context.MODE_PRIVATE).edit()
//                editor.clear()
//                ArrayList<T>()
//            }
//
//        }
//    }


    /**
     * Helps save String data
     */
    private fun putStringData(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    /**
     * Helps get String data
     */
    private fun getStringData(key: String, defVal: String?): String? {
        return sharedPreferences.getString(key, defVal)
    }

    /**
     * Add List of object data with given key to Shared preferences
     */
    private fun <T> addListData(key: String, list: List<T>?) {
        if (list == null) {
            throw RuntimeException("Data can not be null")
        }
        putStringData(key, Gson().toJsonTree(list).asJsonArray.toString())
    }


    /**
     * Retrieve list of objects with given key from Shared preferences
     */
    private fun <T> retrieveListData(key: String, tt: TypeToken<List<T>>): List<T>? {
        val json = getStringData(key, null)
        return if (sharedPreferences.contains(key)) {
            if (json == null) null else Gson().fromJson<List<T>>(json, tt.type)
        } else {
            null
        }
    }

}
