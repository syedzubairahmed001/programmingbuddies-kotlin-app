package com.example.programmingbuddies.util

import androidx.databinding.BindingAdapter
import com.facebook.drawee.view.SimpleDraweeView


@BindingAdapter("image")
fun loadImage(view: SimpleDraweeView, url: String) {
    view.setImageURI(url)
}