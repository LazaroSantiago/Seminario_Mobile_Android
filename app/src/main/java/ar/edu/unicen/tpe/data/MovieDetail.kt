package ar.edu.unicen.tpe.data

class MovieDetail(
    val id: Int,
    val title: String,
    val picture: String?,
    val overview: String,
    val genre: List<String>,
    val vote_average: Float,
)