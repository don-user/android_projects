package ru.yundon.rss.data.api.network

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import ru.yundon.rss.utils.Constants

object RssApiClient {

    private val client = OkHttpClient.Builder().build()

    val ApiRetrofit: RssApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder()
                        .exceptionOnUnreadXml(false)
                        .build()))
            .build()

        return@lazy retrofit.create(RssApi::class.java)
    }
}