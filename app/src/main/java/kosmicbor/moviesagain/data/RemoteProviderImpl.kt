package kosmicbor.moviesagain.data

import kosmicbor.moviesagain.data.dataobjects.Genre
import kosmicbor.moviesagain.data.dataobjects.ListMovie
import kosmicbor.moviesagain.data.dataobjects.MovieDefinition
import kosmicbor.moviesagain.data.retrofit.RemoteDataSource
import kosmicbor.moviesagain.data.retrofit.dto.MoviesListDTO
import kosmicbor.moviesagain.domain.RemoteProvider
import kosmicbor.moviesagain.utils.DataState
import kosmicbor.moviesagain.utils.DataStateError
import kosmicbor.moviesagain.utils.DataStateSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteProviderImpl(
    private val remoteAPI: RemoteDataSource,
    private val apiKey: String,
) : RemoteProvider {

    override suspend fun getRemoteMovieData(id: Int, language: String): Flow<DataState> {

        val responseResult = remoteAPI.getMovie(id, apiKey, language)

        return if (responseResult.isSuccessful) {
            flow {
                val movieDTO = responseResult.body()!!
                val movie = MovieDefinition(
                    backdropPath = movieDTO.backdropPath,
                    budget = movieDTO.budget,
                    genres = movieDTO.genres.map { Genre(id = it.id, name = it.name) },
                    id = movieDTO.id,
                    originalTitle = movieDTO.originalTitle,
                    overview = movieDTO.overview,
                    popularity = movieDTO.popularity,
                    posterPath = movieDTO.posterPath,
                    releaseDate = movieDTO.releaseDate,
                    revenue = movieDTO.revenue,
                    runtime = movieDTO.runtime,
                    status = movieDTO.status,
                    voteAverage = movieDTO.voteAverage,
                    voteCount = movieDTO.voteCount
                )

                emit(DataStateSuccess(movie))
            }
        } else {
            flow {
                emit(DataStateError(responseResult.message()))
            }
        }
    }

    override suspend fun getMoviesList(language: String, page: String): Flow<DataState> {
        val responseResult = remoteAPI.getMoviesList(apiKey, language, page)

        return if (responseResult.isSuccessful) {
            flow {
                val moviesListDTO = responseResult.body() as MoviesListDTO

                val moviesList = moviesListDTO.resultList.map {
                    ListMovie(
                        id = it.id,
                        originalTitle = it.originalTitle,
                        posterPath = it.posterPath,
                        releaseDate = it.releaseDate,
                        voteAverage = it.voteAverage
                    )
                }

                emit(DataStateSuccess(moviesList))
            }
        } else {
            flow {
                emit(DataStateError(responseResult.message()))
            }
        }
    }
}