package com.alenniboris.personalmanager.data.source.remote.service.location

import android.app.Application
import com.alenniboris.personalmanager.BuildConfig
import com.alenniboris.personalmanager.data.source.remote.model.location.CurrentLocationResponseModel
import com.alenniboris.personalmanager.data.source.remote.utils.MyRetrofit
import com.alenniboris.personalmanager.data.source.remote.utils.values.GeoapifyApiValues
import com.andretietz.retrofit.ResponseCache
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ILocationGeoapifyApiService {

    @GET(GeoapifyApiValues.GET_GEOCODE_REVERSE)
    @ResponseCache(5, TimeUnit.MINUTES)
    suspend fun getCurrentLocationOfPhone(
        @Query(GeoapifyApiValues.QUERY_LATITUDE) lat: String,
        @Query(GeoapifyApiValues.QUERY_LONGITUDE) lon: String,
        @Query(GeoapifyApiValues.HEADER_API_KEY) apiKey: String = BuildConfig.GEOAPIFY_API_KEY
    ): CurrentLocationResponseModel

    companion object {
        fun get(
            apl: Application
        ) = MyRetrofit()
            .create<ILocationGeoapifyApiService>(
                apl = apl,
                url = GeoapifyApiValues.BASE_URL
            )
    }
}