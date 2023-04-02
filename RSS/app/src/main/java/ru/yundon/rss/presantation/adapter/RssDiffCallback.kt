package ru.yundon.rss.presantation.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.yundon.rss.domain.model.RssEntity

class RssDiffCallback: DiffUtil.ItemCallback<RssEntity>() {
    override fun areItemsTheSame(oldItem: RssEntity, newItem: RssEntity): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: RssEntity, newItem: RssEntity): Boolean {
        return oldItem == newItem
    }

}