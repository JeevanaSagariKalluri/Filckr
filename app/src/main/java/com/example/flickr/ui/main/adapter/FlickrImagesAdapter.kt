package com.example.flickr.ui.main.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flickr.data.model.PhotosResponseModel
import com.example.flickr.databinding.RowListBinding
import com.squareup.picasso.Picasso

class FlickrImagesAdapter(
    var context: Context,
    var photosList: List<PhotosResponseModel.Photos.Photo>?
) : RecyclerView.Adapter<FlickrImagesAdapter.ImagesInfo>() {

    class ImagesInfo(val binding: RowListBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesInfo =
        ImagesInfo(RowListBinding.inflate(LayoutInflater.from(context), parent, false))

    override fun getItemCount(): Int = photosList!!.size

    override fun onBindViewHolder(holder: ImagesInfo, position: Int) {
        val photo: PhotosResponseModel.Photos.Photo = photosList!![position]
        val farm = photo.farm
        val server = photo.server
        val id = photo.id
        val secret = photo.secret
        val photoPatternURL = "https://farm${farm}.static.flickr.com/${server}/${id}_${secret}.jpg"
        val imageURL = Uri.parse(photoPatternURL)
        Log.e("url: uri: ", imageURL.toString())

        Picasso.get()
            .load(imageURL)
            .resize(100, 100)
            .centerCrop()
            .into(holder.binding.image)
    }
}
