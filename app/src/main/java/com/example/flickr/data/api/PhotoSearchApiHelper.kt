package com.example.flickr.data.api

class PhotoSearchApiHelper(private val userService: Api) {
    suspend fun getPhotosHelper(queryMap:Map<String,String>) = userService.getPhotos(queryMap)
}