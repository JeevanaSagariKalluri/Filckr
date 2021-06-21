package com.example.flickr.data.repository

import com.example.flickr.data.api.PhotoSearchApiHelper

class PhotoSearchRepository(private val userApiHelper: PhotoSearchApiHelper) {

    suspend fun getPhotos(queryMap:Map<String,String>) = userApiHelper.getPhotosHelper(queryMap)
}