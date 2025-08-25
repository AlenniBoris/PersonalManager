package com.alenniboris.personalmanager.data.source.remote.model.location

import com.google.gson.annotations.SerializedName

data class CurrentLocationResponseModel(
    val features: List<Feature?>?,
    val query: Query?,
    val type: String?
) {
    data class Feature(
        @SerializedName("bbox")
        val bBox: List<Double?>?,
        val geometry: Geometry?,
        val properties: Properties?,
        val type: String?
    ) {
        data class Geometry(
            val coordinates: List<Double?>?,
            val type: String?
        )

        data class Properties(
            @SerializedName("address_line1")
            val firstAddressLine: String?,
            @SerializedName("address_line2")
            val secondAddressLine: String?,
            val category: String?,
            val county: String?,
            @SerializedName("county_code")
            val countyCode: String?,
            val city: String?,
            val country: String?,
            @SerializedName("country_code")
            val countryCode: String?,
            val datasource: Datasource?,
            val distance: String?,
            val district: String?,
            val formatted: String?,
            @SerializedName("housenumber")
            val houseNumber: String?,
            val iso3166_2: String?,
            val lat: Double?,
            val lon: Double?,
            val name: String?,
            @SerializedName("place_id")
            val placeId: String?,
            @SerializedName("plus_code")
            val plusCode: String?,
            val postcode: String?,
            val rank: Rank?,
            @SerializedName("result_type")
            val resultType: String?,
            val state: String?,
            @SerializedName("state_code")
            val stateCode: String?,
            val street: String?,
            val suburb: String?,
            val timezone: Timezone?
        ) {
            data class Datasource(
                val attribution: String?,
                val license: String?,
                @SerializedName("sourcename")
                val sourceName: String?,
                val url: String?
            )

            data class Rank(
                val importance: String?,
                val popularity: String?,
                val confidence: String?,
                @SerializedName("confidence_city_level")
                val confidenceCityLevel: String?,
                @SerializedName("confidence_street_level")
                val confidenceStreetLevel: String?
            )

            data class Timezone(
                @SerializedName("abbreviation_DST")
                val abbreviationDst: String?,
                @SerializedName("abbreviation_STD")
                val abbreviationStd: String?,
                val name: String?,
                @SerializedName("offset_DST")
                val offsetDst: String?,
                @SerializedName("offset_DST_seconds")
                val offsetDstSeconds: String?,
                @SerializedName("offset_STD")
                val offsetStd: String?,
                @SerializedName("offset_STD_seconds")
                val offsetStdSeconds: String?
            )
        }
    }

    data class Query(
        val lat: Double?,
        val lon: Double?,
        @SerializedName("plus_code")
        val plusCode: String?
    )
}