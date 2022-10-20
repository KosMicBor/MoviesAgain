package kosmicbor.moviesagain.data.usecases

import kosmicbor.moviesagain.domain.MainRepository
import kosmicbor.moviesagain.domain.usecases.screenusecases.MainScreenUseCase
import kosmicbor.moviesagain.utils.DataState
import kotlinx.coroutines.flow.Flow

class MainScreenUseCaseImpl(private val repository: MainRepository) : MainScreenUseCase {
    override suspend fun getMoviesList(language: String, page: String): Flow<DataState> {
        return repository.getMoviesList(language, page)
    }
}