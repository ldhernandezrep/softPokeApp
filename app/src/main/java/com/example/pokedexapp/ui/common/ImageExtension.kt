package com.example.pokedexapp.ui.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.pokedexapp.R

    fun ImageView.loadImageUrl(url: String){
        Glide.with(context)
            .load(url)
            .centerCrop()
            .override(100)
            .error(R.drawable.ic_search)
            .into(this)
    }