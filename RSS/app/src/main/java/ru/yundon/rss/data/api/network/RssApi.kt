package ru.yundon.rss.data.api.network

import retrofit2.http.GET
import ru.yundon.rss.data.api.dto.RssResponseDto

interface RssApi {

    @GET("breaking/rss")
    suspend fun getBreakingNews(): RssResponseDto

    @GET("hardware-news/rss")
    suspend fun getHardwareNews(): RssResponseDto

    @GET("gadgets/rss")
    suspend fun getGadgetsNews(): RssResponseDto

    @GET("software-news/rss")
    suspend fun getSoftwareNews(): RssResponseDto

    @GET("games/rss")
    suspend fun getGameNews(): RssResponseDto
}