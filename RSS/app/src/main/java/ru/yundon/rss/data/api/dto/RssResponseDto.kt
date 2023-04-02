package ru.yundon.rss.data.api.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml


@Xml(name = "rss")
data class RssResponseDto(
    @Element(name="channel")
    val channelDto: RssChannelDto
)






