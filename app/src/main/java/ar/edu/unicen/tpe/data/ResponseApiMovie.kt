package ar.edu.unicen.tpe.data

import ar.edu.unicen.tpe.entity.Movie

class ResponseApiMovie(
    var error: String?,
    var movieList: List<Movie>?
)