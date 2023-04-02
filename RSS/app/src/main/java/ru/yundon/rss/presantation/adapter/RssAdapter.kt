package ru.yundon.rss.presantation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.squareup.picasso.Picasso
import ru.yundon.rss.R
import ru.yundon.rss.databinding.RssNewsItemBinding
import ru.yundon.rss.domain.model.RssEntity


class RssAdapter : ListAdapter<RssEntity, RssViewHolder>(RssDiffCallback()) {

    var itemClickListener: ((RssEntity) -> Unit)? = null
    var itemFavoritesListener: ((RssEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RssViewHolder {
        val binding = RssNewsItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RssViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RssViewHolder, position: Int) {

        val rssNewsItem = getItem(position)

        with(holder.itemBinding){
            dateNews.text = rssNewsItem.pubDate
            nameNews.text = rssNewsItem.title

            Picasso.get()
                .load(rssNewsItem.imageUrl)
                .fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(image)

            favoritesImage.setImageResource(
                if (rssNewsItem.isFavorites) R.drawable.favorite_orange
                else R.drawable.favorite_border_orange
            )

            favoritesImage.setOnClickListener {
                itemFavoritesListener?.invoke(rssNewsItem)
            }

            root.setOnClickListener {
                itemClickListener?.invoke(rssNewsItem)
            }

        }
    }
}

