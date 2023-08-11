package com.megaulorder.aboba

import android.app.Application
import com.megaulorder.aboba.fetch.ApiMethod
import com.megaulorder.aboba.fetch.DataRepository
import kotlinx.coroutines.MainScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App: Application() {

    lateinit var repo: DataRepository

    override fun onCreate() {
        super.onCreate()

        appInstance = this

        val apiService: ApiMethod = Retrofit.Builder()
            .baseUrl("https://random-words-api.vercel.app/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiMethod::class.java)

        repo = DataRepository(apiService, this)
    }

    companion object {
        lateinit var appInstance: App
    }
}