package kosmicbor.moviesagain.ui.moviedescriptionscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kosmicbor.moviesagain.data.dataobjects.MovieDefinition
import kosmicbor.moviesagain.domain.usecases.screenusecases.MovieDescriptionScreenUseCase
import kosmicbor.moviesagain.utils.*
import kotlinx.coroutines.launch

class MovieDescriptionScreenViewModel(private val usecase: MovieDescriptionScreenUseCase) :
    ViewModel() {

    private val _liveDataToObserve = MutableLiveData<AppState>()
    val liveDataToObserve: LiveData<AppState> = _liveDataToObserve

    fun getMovieDescription(id: Int, language: String) {

        viewModelScope.launch {

            usecase.getMovieData(id, language).collect { dataState ->
                when (dataState) {
                    is DataStateSuccess<*> -> {
                        val data = dataState.value as MovieDefinition
                        _liveDataToObserve.postValue(AppStateSuccess(data))
                    }

                    is DataStateError<*> -> {
                        val dataError = dataState.error as String
                        _liveDataToObserve.postValue(AppStateError(dataError))
                    }
                }
            }
        }
    }
}