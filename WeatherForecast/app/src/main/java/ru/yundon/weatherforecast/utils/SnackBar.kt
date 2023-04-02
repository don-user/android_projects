package ru.yundon.weatherforecast.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
}