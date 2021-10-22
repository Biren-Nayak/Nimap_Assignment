package com.example.nimapassignment.network

import com.example.nimapassignment.models.Records
import retrofit2.http.GET

interface API {
    @GET("testdata.json")
    suspend fun getRecords():Records

}
