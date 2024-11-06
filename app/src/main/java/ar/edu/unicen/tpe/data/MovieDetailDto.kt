package ar.edu.unicen.tpe.data


import ar.edu.unicen.tpe.entity.Genre
import com.google.gson.annotations.SerializedName


data class MovieDetailDto (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("poster_path") val picture: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("vote_average") val vote_average: Float) {

    fun toMovieDetail(): MovieDetail {
        return MovieDetail(
            id = id,
            title = title,
            picture = picture,
            overview = overview,
            genre = genres.map{it.name},
            vote_average = vote_average
        )
    }

}