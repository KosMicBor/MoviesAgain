package kosmicbor.moviesagain.data.retrofit

import kosmicbor.moviesagain.data.retrofit.dto.ListMovieDTO
import kosmicbor.moviesagain.data.retrofit.dto.MoviesListDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteDataSource {

    @GET("movie/{movie_id}")
    suspend fun getMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String

    ): Response<ListMovieDTO>

    @GET("discover/movie")
    suspend fun getMoviesList(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: String
    ): Response<MoviesListDTO>
}