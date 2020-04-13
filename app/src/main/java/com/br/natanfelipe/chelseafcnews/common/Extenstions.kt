package com.br.natanfelipe.chelseafcnews.common

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.br.natanfelipe.chelseafcnews.R

@BindingAdapter("app:image")
fun loadImage(imageView: AppCompatImageView, url: String){
   imageView.load(url) {
       crossfade(true)
       placeholder(R.drawable.ic_image_black)
   }
}