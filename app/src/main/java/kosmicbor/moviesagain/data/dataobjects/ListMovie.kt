package kosmicbor.moviesagain.data.dataobjects

data class ListMovie(
    val id: Int,
    val originalTitle: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Number
)