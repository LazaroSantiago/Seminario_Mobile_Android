package ar.edu.unicen.tpe.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.tpe.MovieAdapter
import ar.edu.unicen.tpe.R
import ar.edu.unicen.tpe.data.Errors
import ar.edu.unicen.tpe.data.MainViewModel
import ar.edu.unicen.tpe.databinding.ActivityMainBinding
import ar.edu.unicen.tpe.entity.Movie
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToUi()
        subscribeToViewModel()
    }

    private fun subscribeToUi() {
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getPopularMovies(getString(R.string.language))

        binding.refreshButton.setOnClickListener{
            viewModel.getPopularMovies(getString(R.string.language))
        }
    }

    private fun subscribeToViewModel() {
        viewModel.loading.onEach { loading ->
            binding.progressBar.visibility =
                if (loading) View.VISIBLE else View.GONE
        }.launchIn(lifecycleScope)

        viewModel.movies.onEach { movies ->
            binding.movieList.adapter = MovieAdapter(
                movies = movies ?: emptyList(),
                onMovieClick = { movie : Movie ->
                    val intent = Intent(this, DetailedActivity::class.java)
                    val id: Int = movie.id
                    intent.putExtra("id", id)
                    startActivity(intent)
                }
            )
        }.launchIn(lifecycleScope)

        viewModel.errorMessage.onEach { errorMessage ->
            binding.refreshButton.visibility =
                if (errorMessage!=null) View.VISIBLE else View.GONE

            val error = Errors()
            when(errorMessage){
                error.noInternet -> Toast.makeText(this, R.string.nointernet, Toast.LENGTH_SHORT).show()
                error.unexpected -> Toast.makeText(this, R.string.errorunexpected, Toast.LENGTH_SHORT).show()
                error.noContent -> Toast.makeText(this, R.string.nocontent, Toast.LENGTH_SHORT).show()
            }
        }.launchIn(lifecycleScope)

    }

}