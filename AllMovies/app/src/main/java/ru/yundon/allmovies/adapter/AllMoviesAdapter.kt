package ru.yundon.allmovies.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.yundon.allmovies.R
import ru.yundon.allmovies.data.local.database.AllMoviesListEntity
import ru.yundon.allmovies.databinding.ItemAllMoviesBinding
import ru.yundon.allmovies.utils.Constants.RELEASE_TEXT


class AllMoviesAdapter(private val onItemClickListener: ItemClickListener)
    : RecyclerView.Adapter<AllMoviesAdapter.AllMoviesViewHolder>() {

    private lateinit var binding: ItemAllMoviesBinding

    private val allMoviesListEntity: MutableList<AllMoviesListEntity> = mutableListOf()

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): AllMoviesViewHolder {
        binding = ItemAllMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllMoviesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllMoviesViewHolder, position: Int) {
        holder.bind(allMoviesListEntity[position], onItemClickListener)

    }
    override fun getItemCount(): Int = allMoviesListEntity.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(listMovies: List<AllMoviesListEntity>){
        allMoviesListEntity.clear()
        allMoviesListEntity.addAll(listMovies)
        notifyDataSetChanged()
    }

    class AllMoviesViewHolder(private val viewBinding: ItemAllMoviesBinding): RecyclerView.ViewHolder(viewBinding.root){

        @SuppressLint("SetTextI18n")
        fun bind(moviesListEntity: AllMoviesListEntity, onItemCallback: ItemClickListener) = with(viewBinding){

            tViewTitle.text = moviesListEntity.title
            tViewRelease.text = "$RELEASE_TEXT ${moviesListEntity.releaseDate}"
            tViewRating.text = moviesListEntity.voteAverage.toString()
            ratingBar.rating = moviesListEntity.voteAverage?.toFloat() ?: 0F

            Picasso.get()
                .load(moviesListEntity.backdropPath)
                .fit()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)

            favoritesImage.setImageResource(
                if (moviesListEntity.isFavourite) R.drawable.favorite_black
                else R.drawable.favorite_border_black
            )
            favoritesImage.setOnClickListener{
                onItemCallback.onFavoriteClick(moviesListEntity.id)
            }
            root.setOnClickListener{
                onItemCallback.onItemClick(moviesListEntity.title, moviesListEntity.id)
            }
        }
    }


    interface ItemClickListener{
        fun onItemClick(title: String?, id: Int)

        fun onFavoriteClick(id: Int){

        }
    }
}

