package ru.yundon.rss.data.api.dto

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "channel")
data class RssChannelDto(
    @Element(name="item")
    val itemDtoList: List<RssItemDto>
)