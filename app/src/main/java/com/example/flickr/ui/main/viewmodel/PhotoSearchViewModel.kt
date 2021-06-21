package com.example.flickr.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.flickr.data.repository.PhotoSearchRepository
import com.example.flickr.utils.Resource
import kotlinx.coroutines.Dispatchers

class PhotoSearchViewModel(private val photoSearchRepository: PhotoSearchRepository) : ViewModel() {

    fun getPhotos(queryMap:Map<String,String>) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = photoSearchRepository.getPhotos(queryMap)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}