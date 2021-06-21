package com.example.flickr.data.api

import com.example.flickr.data.model.PhotosResponseModel
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    @GET("services/rest/")
    suspend fun getPhotos(@QueryMap photoSearch: Map<String, String>): PhotosResponseModel
}