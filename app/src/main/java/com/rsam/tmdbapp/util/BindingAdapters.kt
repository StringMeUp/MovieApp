package com.rsam.tmdbapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("app:image")
fun setImage(view: ImageView, url: String?) {
    url?.let {
        GlideApp.with(view)
            .load(Constants.IMAGE_PATH + url)
            .centerCrop()
            .into(view)
    }
}