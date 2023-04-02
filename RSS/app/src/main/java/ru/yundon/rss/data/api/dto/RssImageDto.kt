package ru.yundon.rss.data.api.dto

import com.tickaroo.tikxml.annotation.Attribute
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "enclosure")
data class RssImageDto(
    @Attribute(name="url")
    val url: String
)