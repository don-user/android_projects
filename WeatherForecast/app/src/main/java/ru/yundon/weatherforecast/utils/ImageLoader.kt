package ru.yundon.weatherforecast.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.yundon.weatherforecast.R

object ImageLoader {

    fun loadImage(view: ImageView, url: String?, placeholder: Int = R.drawable.ic_minimize){
        Picasso.get()
            .load(url)
            .placeholder(placeholder)
            .error(placeholder)
            .into(view)
    }
}