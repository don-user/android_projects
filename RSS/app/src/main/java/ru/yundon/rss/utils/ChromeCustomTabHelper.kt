package ru.yundon.rss.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.content.res.AppCompatResources
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.graphics.drawable.toBitmap
import ru.yundon.rss.R

object ChromeCustomTabHelper {

    fun openCct(context: Context, link: String){
        val colorOrange = ContextCompat.getColor(context, R.color.grey_200)
        val setColors = CustomTabColorSchemeParams.Builder()
            .setToolbarColor(colorOrange)
            .build()

        CustomTabsIntent.Builder()
            .setDefaultColorSchemeParams(setColors)
            .setStartAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
            .setExitAnimations(context,android.R.anim.fade_in, android.R.anim.fade_out)
            .let { ctb ->
                AppCompatResources.getDrawable(context, R.drawable.arrow_back)!!.let {
                    ctb.setCloseButtonIcon(it.toBitmap())
                }
            }
            .build()
            .launchUrl(context, Uri.parse(link))
    }
}