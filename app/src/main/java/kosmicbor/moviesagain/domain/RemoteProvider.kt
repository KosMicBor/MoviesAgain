package kosmicbor.moviesagain.domain

import kosmicbor.moviesagain.utils.DataState
import kotlinx.coroutines.flow.Flow

interface RemoteProvider {
    suspend fun getRemoteMovieData(id: Int, language: String): Flow<DataState>
    suspend fun getMoviesList(language: String, page: String): Flow<DataState>
}