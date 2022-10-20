package kosmicbor.moviesagain.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class MovieDefinitionDTO(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<GenreDTO>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Number,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Int,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("vote_average")
    val voteAverage: Number,
    @SerializedName("vote_count")
    val voteCount: Number
)