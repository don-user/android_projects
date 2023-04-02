package ru.yundon.allmovies.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun showSnackbar(view: View, message: String){
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}