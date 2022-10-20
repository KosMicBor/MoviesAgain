package kosmicbor.moviesagain.data

import kosmicbor.moviesagain.data.dataobjects.ListMovie
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
                val movie = ListMovie(
                    id = responseResult.body()!!.id,
                    originalTitle = responseResult.body()!!.originalTitle,
                    posterPath = responseResult.body()!!.posterPath,
                    releaseDate = responseResult.body()!!.releaseDate,
                    voteAverage = responseResult.body()!!.voteAverage
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