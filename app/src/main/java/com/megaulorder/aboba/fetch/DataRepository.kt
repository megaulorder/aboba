package com.megaulorder.aboba.fetch

import android.content.Context
import android.util.Log
import com.megaulorder.aboba.R
import retrofit2.http.GET

class DataRepository(
    private val apiMethod: ApiMethod,
    private val context: Context,
) {

    private var cache: String? = null

    suspend fun getCachedData(isForced: Boolean): String {
        return if (isForced || cache == null) {
            cache = getData()
            Log.d("ZHOPA", "cached value: $cache")
            cache!!
        } else {
            cache!!
        }
    }

    private suspend fun getData(): String {
        return try {
            val data: ApiData = apiMethod.getData().first()
            context.getString(R.string.network_fetch_fetched_text, data.word, data.definition)
        } catch (e: Exception) {
            context.getString(R.string.network_fetch_error_text, e.message)
        }
    }
}

data class ApiData(
    val word: String,
    val definition: String,
)

interface ApiMethod {
    @GET("word")
    suspend fun getData(): List<ApiData>
}