package com.example.dogsapplication.doghistory

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogsapplication.R


/**
 * use glide library to download images from url string
 *
 * @param imgView
 * @param imgUrl
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.ic_baseline_photo)
                .error(R.drawable.ic_baseline_broken_image))
            .into(imgView)
    }
}
