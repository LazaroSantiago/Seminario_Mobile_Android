package ar.edu.unicen.tpe.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.tpe.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieDatabaseRepository: MovieDatabaseRepository
): ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _movies = MutableStateFlow<List<Movie>?>(emptyList())
    val movies = _movies.asStateFlow()

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie = _movie.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _errorMessageDetail = MutableStateFlow<String?>(null)
    val errorMessageDetail: StateFlow<String?> = _errorMessageDetail.asStateFlow()


    fun getPopularMovies(language: String) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessage.value = null

            val response = movieDatabaseRepository.getPopularMovies(language)

            if (response.movieList != null) {
                _movies.value = response.movieList
            } else {
                _errorMessage.value = response.error
                _movies.value = ResponseApiMovie(response.error,null).movieList
            }

            _loading.value = false
        }
    }

    fun getMovieById(language: String, id: Int) {
        viewModelScope.launch {
            _loading.value = true
            _errorMessageDetail.value = null

            val response = movieDatabaseRepository.getMovieById(language, id)

            if (response.movieDetail != null) {
                _movie.value = response.movieDetail
            } else {
                _errorMessageDetail.value = response.error
                _movie.value = ResponseApiMovieDetail(response.error,null).movieDetail
            }

            _loading.value = false
        }
    }

}