package ar.edu.unicen.tpe.data
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String,
        @Query("language") language: String
    ): Response<MovieResponse>

    @GET("movie/{id}")
    suspend fun getMovieById(
        @Header("Authorization") authHeader: String,
        @Path("id") id: Int,
        @Query("language") language: String
    ): Response<MovieDetailDto>
}