package kosmicbor.moviesagain.data

import kosmicbor.moviesagain.domain.MainRepository
import kosmicbor.moviesagain.domain.RemoteProvider
import kosmicbor.moviesagain.utils.DataState
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(private val remoteProvider: RemoteProvider) : MainRepository {

    override suspend fun getMovieDescription(id: Int, language: String): Flow<DataState> {
        return remoteProvider.getRemoteMovieData(id, language)
    }

    override suspend fun getMoviesList(language: String, page: String): Flow<DataState> {
        return remoteProvider.getMoviesList(language, page)
    }
}