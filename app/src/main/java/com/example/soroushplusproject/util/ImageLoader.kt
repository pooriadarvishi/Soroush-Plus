package com.example.soroushplusproject.util

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImage(srcImage: String) {
    Glide.with(this)
        .load(srcImage)
        .into(this)
}