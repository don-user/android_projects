package ru.yundon.rss.utils

import android.content.Context
import android.widget.Toast

object MakeToast {

    fun toast(context: Context, text: String){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}