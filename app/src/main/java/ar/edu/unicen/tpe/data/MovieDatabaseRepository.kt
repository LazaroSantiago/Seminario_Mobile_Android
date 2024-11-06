package ar.edu.unicen.tpe.data

import javax.inject.Inject

class MovieDatabaseRepository @Inject constructor(
    private val datasource: MovieRemoteDatasource
) {
    suspend fun getPopularMovies(language:String): ResponseApiMovie {
        return datasource.getPopularMovies(language)
    }


    suspend fun getMovieById(language:String,id:Int): ResponseApiMovieDetail {
        return datasource.getMovieById(language,id)
    }
}