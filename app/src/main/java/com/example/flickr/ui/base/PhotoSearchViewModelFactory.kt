package com.example.flickr.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.flickr.data.api.PhotoSearchApiHelper
import com.example.flickr.data.repository.PhotoSearchRepository
import com.example.flickr.ui.main.viewmodel.PhotoSearchViewModel

class PhotoSearchViewModelFactory(private val photoSearchApiHelper: PhotoSearchApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoSearchViewModel::class.java)) {
            return PhotoSearchViewModel(PhotoSearchRepository(photoSearchApiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}