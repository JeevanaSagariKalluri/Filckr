package com.example.flickr.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flickr.R
import com.example.flickr.data.api.PhotoSearchApiHelper
import com.example.flickr.data.api.RetrofitBuilder
import com.example.flickr.data.model.PhotosResponseModel
import com.example.flickr.databinding.ActivityPhotoSearchListBinding
import com.example.flickr.ui.base.PhotoSearchViewModelFactory
import com.example.flickr.ui.main.adapter.FlickrImagesAdapter
import com.example.flickr.ui.main.viewmodel.PhotoSearchViewModel
import com.example.flickr.utils.Status

class PhotoSearchListActivity : AppCompatActivity() {
    private lateinit var photoSearchViewModel: PhotoSearchViewModel
    private lateinit var binding: ActivityPhotoSearchListBinding
    lateinit var flickrAdapter: FlickrImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoSearchListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupObservers()

    }

    private fun setupObservers() {
        photoSearchViewModel = ViewModelProvider(this,
            PhotoSearchViewModelFactory(PhotoSearchApiHelper(RetrofitBuilder.userService))
        ).get(PhotoSearchViewModel::class.java)
    }

    private fun setupPhotosViewModel(query: String) {

    val queryMap: MutableMap<String, String> = HashMap()
    queryMap["method"] = "flickr.photos.search"
    queryMap["api_key"] = "3e7cc266ae2b0e0d78e279ce8e361736"
    queryMap["format"] = "json"
    queryMap["nojsoncallback"] = "1"
    queryMap["safe_search"] = "1"
    queryMap["text"] = query

        photoSearchViewModel.getPhotos(queryMap).observe(this, Observer {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        binding.recyclerView.visibility = View.VISIBLE
                        binding.emptyTv.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        resource.data?.let {
                            loadImagesData(it.photos.photo)
                        }
                    }
                    Status.ERROR -> {
                        binding.emptyTv.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                    Status.LOADING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                        binding.emptyTv.visibility = View.GONE
                    }
                }
            }
        })
    }


    private fun loadImagesData(photosList: List<PhotosResponseModel.Photos.Photo>?) {
        flickrAdapter = FlickrImagesAdapter(this@PhotoSearchListActivity, photosList)
        binding.recyclerView.adapter = flickrAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu!!.findItem(R.id.actionSearch)
            .actionView as SearchView
        if (null != searchView) {
            searchView.setSearchableInfo(
                searchManager
                    .getSearchableInfo(componentName)
            )
            searchView.setIconifiedByDefault(false)
        }

        val queryTextListener: SearchView.OnQueryTextListener = object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                // This is your adapter that will be filtered
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Here you can get the value "query" which is entered in the search box
               if (query != null) {
                   setupPhotosViewModel(query)
                   searchView.clearFocus()
               }
                return true
            }
        }
        searchView.setOnQueryTextListener(queryTextListener)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.actionSearch){

        }
        return super.onOptionsItemSelected(item)
    }
}
