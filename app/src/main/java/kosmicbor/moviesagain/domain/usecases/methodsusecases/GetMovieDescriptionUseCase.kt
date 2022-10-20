package kosmicbor.moviesagain.domain.usecases.methodsusecases

import kosmicbor.moviesagain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface GetMovieDescriptionUseCase {
    suspend fun getMovieData(id: Int, language: String): Flow<DataState>
}