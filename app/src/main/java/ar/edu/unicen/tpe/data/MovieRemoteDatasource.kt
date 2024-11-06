package ar.edu.unicen.tpe.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDatasource @Inject constructor(
    private val movieApi: MovieApi,
    private val tokenKey: String,
    ) {

    suspend fun getPopularMovies(language: String): ResponseApiMovie {
        val errors = Errors()
        return withContext(Dispatchers.IO) {
            val responseAPIMovie = ResponseApiMovie(null,null)
            try {
                val result = movieApi.getPopularMovies("Bearer $tokenKey", language)

                if (result.isSuccessful) {
                    if(result.body()?.movies.isNullOrEmpty()){
                        responseAPIMovie.error = errors.noContent
                    }
                    responseAPIMovie.movieList = result.body()?.movies?.map { it.toMovie() }
                }

            }
            catch (e: IOException) {
                responseAPIMovie.error = errors.noInternet
            }
            catch (e: Exception) {
                responseAPIMovie.error = errors.unexpected
                e.printStackTrace()
            }
            return@withContext responseAPIMovie
        }
    }

    suspend fun getMovieById(language: String, id: Int): ResponseApiMovieDetail {
        val errors = Errors()
        val responseAPIMovieDetail = ResponseApiMovieDetail(null,null)
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovieById("Bearer $tokenKey", id, language)
                val movieResponse = response.body()
                if (response.isSuccessful) {
                    if (response.body() == null) {
                        responseAPIMovieDetail.error = errors.noContent
                    } else {
                        responseAPIMovieDetail.movieDetail = movieResponse?.toMovieDetail()
                        responseAPIMovieDetail.error= null
                    }
                }

            } catch (e: IOException) {
                responseAPIMovieDetail.error = errors.noInternet
                return@withContext responseAPIMovieDetail
            } catch (e: Exception) {
                responseAPIMovieDetail.error = errors.unexpected
                e.printStackTrace()
            }
            return@withContext responseAPIMovieDetail
        }
    }
}

