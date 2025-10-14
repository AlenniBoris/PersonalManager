package com.alenniboris.personalmanager.data.repository

import com.alenniboris.personalmanager.data.source.remote.model.location.CurrentLocationResponseModel
import com.alenniboris.personalmanager.data.source.remote.service.location.ILocationGeoapifyApiService
import com.alenniboris.personalmanager.domain.model.common.CustomResultModelDomain
import com.alenniboris.personalmanager.domain.model.common.IAppDispatchers
import com.alenniboris.personalmanager.domain.utils.GsonUtil.fromJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class LocationRepositoryImplTest {

    private val LOCATION_RESPONSE: CurrentLocationResponseModel = """
        {
            "type": "FeatureCollection",
            "features": [
                {
                    "type": "Feature",
                    "properties": {
                        "datasource": {
                            "sourcename": "openstreetmap",
                            "attribution": "© OpenStreetMap contributors",
                            "license": "Open Database License",
                            "url": "https://www.openstreetmap.org/copyright"
                        },
                        "country": "Belarus",
                        "country_code": "by",
                        "city": "Minsk",
                        "postcode": "220088",
                        "district": "Partyzanski District",
                        "street": "вуліца Фрунзэ",
                        "housenumber": "5",
                        "iso3166_2": "BY-HM",
                        "lon": 27.574679089715147,
                        "lat": 53.906065600000005,
                        "distance": 0,
                        "result_type": "building",
                        "formatted": "Belarus, 220088 Minsk, вуліца Фрунзэ, 5",
                        "address_line1": "Belarus",
                        "address_line2": "220088 Minsk, вуліца Фрунзэ, 5",
                        "category": "building",
                        "timezone": {
                            "name": "Europe/Minsk",
                            "offset_STD": "+03:00",
                            "offset_STD_seconds": 10800,
                            "offset_DST": "+03:00",
                            "offset_DST_seconds": 10800
                        },
                        "plus_code": "9G59WH4F+CV",
                        "plus_code_short": "4F+CV Minsk, Belarus",
                        "rank": {
                            "importance": 0.00000999999999995449,
                            "popularity": 8.995467104553104
                        },
                        "place_id": "518805382b1e933b4059ec0324f5f9f34a40f00102f9013874820100000000c00203"
                    },
                    "geometry": {
                        "type": "Point",
                        "coordinates": [27.574679089715147, 53.906065600000005]
                    },
                    "bbox": [27.5744563, 53.9058665, 27.5751202, 53.9062827]
                }
            ], "query": {
                "lat": 53.906,
                "lon": 27.575,
                "plus_code": "9G59WH4F+CX"
            }
        }
    """.trimIndent().fromJson<CurrentLocationResponseModel>()


    private val testScheduler = TestCoroutineScheduler()
    private val dispatchers: IAppDispatchers = object : IAppDispatchers {
        override val IO: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
        override val DEFAULT: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
        override val MAIN: CoroutineDispatcher = StandardTestDispatcher(testScheduler)
    }
    private val locationGeoapifyApiService: ILocationGeoapifyApiService =
        object : ILocationGeoapifyApiService {
            override suspend fun getCurrentLocationOfPhone(
                lat: String,
                lon: String,
                apiKey: String
            ): CurrentLocationResponseModel {
                return LOCATION_RESPONSE
            }
        }

    private val locationRepository: LocationRepositoryImpl = LocationRepositoryImpl(
        locationGeoapifyApiService = locationGeoapifyApiService,
        dispatchers = dispatchers
    )

    @Test
    fun `test if response is operated correctly`() = runTest(testScheduler) {
        val res = locationRepository.getCurrentLocation(
            lat = 53.906,
            lon = 27.575
        )
        assert(res is CustomResultModelDomain.Success)
        assertEquals(res.result, "Minsk")
    }
}