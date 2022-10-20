package kosmicbor.moviesagain.domain.usecases.methodsusecases

import kosmicbor.moviesagain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface getMoviesListUseCase {
    suspend fun getMoviesList(language: String, page: String): Flow<DataState>
}