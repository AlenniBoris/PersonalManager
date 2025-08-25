package com.alenniboris.personalmanager.data.source.remote.utils

import android.app.Application
import com.alenniboris.personalmanager.BuildConfig
import com.alenniboris.personalmanager.data.source.remote.utils.OkHttpCache.setOkhttpCache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyRetrofit {

    inline fun <reified T> create(
        apl: Application,
        url: String,
        isSafe: Boolean = !BuildConfig.DEBUG,
        connectTimeout: Long = 30,
        writeTimeout: Long = 30,
        readTimeout: Long = 120
    ): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                MyOkHttpClient(
                    isSafe = isSafe,
                    connectTimeout = connectTimeout,
                    writeTimeout = writeTimeout,
                    readTimeout = readTimeout
                ).get()
            )
            .build()
            .setOkhttpCache(apl)
            .create(T::class.java)
    }

}