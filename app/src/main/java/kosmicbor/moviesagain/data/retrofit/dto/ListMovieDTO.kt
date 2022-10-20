package kosmicbor.moviesagain.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class ListMovieDTO(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val originalTitle: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("vote_average")
    val voteAverage: Number
)
