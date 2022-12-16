package com.ymaskin.thelionking.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

fun ImageView.setImage(
    context: Context,
    url: String,
    width: Int,
    height: Int,
    placeholder: Drawable?
) {
    val options = RequestOptions()
        .override(width, height)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(placeholder)

    Glide.with(context)
        .load(url)
        .apply(options)
        .into(this)
}