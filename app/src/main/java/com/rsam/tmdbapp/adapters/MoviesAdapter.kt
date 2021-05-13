package com.rsam.tmdbapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.rsam.tmdbapp.R
import com.rsam.tmdbapp.data.Movie
import com.rsam.tmdbapp.databinding.MoviesCardBinding
import com.rsam.tmdbapp.util.ImageHelper

class MoviesAdapter(
    private var cardClickListener: OnCardClickListener,
    private var buttonClickListener: OnButtonClickListener
) : RecyclerView.Adapter<MoviesAdapter.NewsViewHolder>() {

    var moviesList = ArrayList<Movie>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    class NewsViewHolder(
        private val binding: MoviesCardBinding,
        private val cardClickListener: OnCardClickListener,
        private val buttonClickListener: OnButtonClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.run {
                ImageHelper.loadRemoteImage(movieImageView, movie.posterPath, movieImageView)
                movieTitleTextView.text = movie.title
                releaseDateTextView.text = movie.releaseDate
            }

            itemView.setOnClickListener {
                cardClickListener.onCardClicked(movie.id)
            }

            binding.addFavoriteButton.setOnClickListener {
                buttonClickListener.onButtonClicked(movie)
            }

            binding.cardViewId.animation = AnimationUtils.loadAnimation(
                binding.cardViewId.context,
                R.anim.recycler_anim
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = DataBindingUtil.inflate<MoviesCardBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movies_card, parent, false
        )
        return NewsViewHolder(
            binding,
            cardClickListener,
            buttonClickListener
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int = moviesList.size
}

interface OnCardClickListener {
    fun onCardClicked(movieId: Int)
}

interface OnButtonClickListener {
    fun onButtonClicked(movie: Movie)
}