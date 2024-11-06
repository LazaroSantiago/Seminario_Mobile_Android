package ar.edu.unicen.tpe.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.tpe.R
import ar.edu.unicen.tpe.data.Errors
import ar.edu.unicen.tpe.data.MainViewModel
import ar.edu.unicen.tpe.databinding.ActivityDetailedBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailedActivity: AppCompatActivity() {
    private lateinit var binding: ActivityDetailedBinding

    private val viewModel by viewModels<MainViewModel>()
    private val pictureUrl : String = "https://image.tmdb.org/t/p/w500/"

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        subscribeToUi()
        subscribeToViewModel()
        returnToMain()
    }

    private fun subscribeToUi(){
        enableEdgeToEdge()
        binding = ActivityDetailedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra("id", 0)
        val language = getString(R.string.language)
        viewModel.getMovieById(language,id)

        binding.refreshButton.setOnClickListener{
            viewModel.getMovieById(getString(R.string.language), id)
        }
    }

    private fun returnToMain() {
        binding.returnButton.setOnClickListener {
            finish()
        }
    }

    private fun subscribeToViewModel() {
        viewModel.loading.onEach { loading ->
            binding.progressBar.visibility =
                if (loading) View.VISIBLE else View.GONE
        }.launchIn(lifecycleScope)
        viewModel.movie.onEach { movieDetail ->

            if(movieDetail!=null) {
                val concatenatedGenres = movieDetail.genre.joinToString(" | ")

                binding.movieName.text = movieDetail.title
                binding.overview.text = movieDetail.overview
                binding.genres.text = getString(R.string.genresprefix, concatenatedGenres)
                binding.score.text = getString(R.string.averagetext, movieDetail.vote_average.toString())

                if(movieDetail.picture!=null){
                    Glide.with(this)
                        .load(pictureUrl + movieDetail.picture)
                        .transform(RoundedCorners(16))
                        .into(binding.moviePoster)
                }

            }
            else {
                binding.movieName.text = ""
                binding.overview.text = ""
                binding.genres.text = ""
                binding.score.text = " "
            }

        }.launchIn(lifecycleScope)

            viewModel.errorMessageDetail.onEach { errorMessageDetail ->
                binding.refreshButton.visibility =
                    if (errorMessageDetail!=null) View.VISIBLE else View.GONE

                val error = Errors()
                when(errorMessageDetail){
                    error.noInternet -> Toast.makeText(this, R.string.nointernet, Toast.LENGTH_SHORT).show()
                    error.unexpected -> Toast.makeText(this, R.string.errorunexpected, Toast.LENGTH_SHORT).show()
                    error.noContent -> Toast.makeText(this, R.string.nocontent, Toast.LENGTH_SHORT).show()
                }
            }.launchIn(lifecycleScope)
    }
}