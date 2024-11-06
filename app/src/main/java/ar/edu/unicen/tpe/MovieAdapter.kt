package ar.edu.unicen.tpe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ar.edu.unicen.tpe.databinding.ListItemMovieBinding
import ar.edu.unicen.tpe.entity.Movie
import com.bumptech.glide.Glide

class MovieAdapter(
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
): RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    val imgUrl : String = "https://image.tmdb.org/t/p/w500/"


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemMovieBinding.inflate(layoutInflater, parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieViewHolder(
        private val binding: ListItemMovieBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind (movie: Movie){

            binding.movieName.text = movie.title

            if(movie.picture!=null){
                Glide.with(itemView.context)
                    .load(imgUrl + movie.picture)
                    .into(binding.moviePoster)
            }

            binding.root.setOnClickListener{
                onMovieClick(movie)
            }
        }

    }
}