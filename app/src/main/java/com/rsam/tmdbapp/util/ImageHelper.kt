package com.rsam.tmdbapp.util

import android.view.View
import android.widget.ImageView

object ImageHelper {
    fun loadRemoteImage(view: View, url: String, imageView: ImageView) {
        GlideApp.with(view)
            .load(Constants.IMAGE_PATH + url)
            .into(imageView)
    }
}