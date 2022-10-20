package kosmicbor.moviesagain.data.usecases

import kosmicbor.moviesagain.domain.MainRepository
import kosmicbor.moviesagain.domain.usecases.screenusecases.MovieDescriptionScreenUseCase
import kosmicbor.moviesagain.utils.DataState
import kotlinx.coroutines.flow.Flow

class MovieDescriptionScreenUsecaseImpl(private val repository: MainRepository) :
    MovieDescriptionScreenUseCase {
    override suspend fun getMovieData(id: Int, language: String): Flow<DataState> {
        return repository.getMovieDescription(id, language)
    }
}