package kosmicbor.moviesagain.data.retrofit.dto

import com.google.gson.annotations.SerializedName

data class MoviesListDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultList: List<ListMovieDTO>
)