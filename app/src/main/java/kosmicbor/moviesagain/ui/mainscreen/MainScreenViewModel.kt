package kosmicbor.moviesagain.ui.mainscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kosmicbor.moviesagain.data.dataobjects.ListMovie
import kosmicbor.moviesagain.domain.usecases.screenusecases.MainScreenUseCase
import kosmicbor.moviesagain.utils.*
import kotlinx.coroutines.launch

class MainScreenViewModel(private val usecase: MainScreenUseCase) : ViewModel() {

    private val _dataToObserve = MutableLiveData<AppState>()
    val dataToObserve: LiveData<AppState> = _dataToObserve

    private val moviesList = mutableListOf<ListMovie>()

    fun getMoviesList(language: String, page: String) {
        _dataToObserve.postValue(AppStateLoading)

        viewModelScope.launch {
            usecase.getMoviesList(language, page).collect { dataState ->
                when (dataState) {
                    is DataStateSuccess<*> -> {
                        val data = dataState.value as List<ListMovie>
                        moviesList.addAll(data)
                        _dataToObserve.postValue(AppStateSuccess(moviesList))
                    }

                    is DataStateError<*> -> {
                        val dataError = dataState.error as String
                        _dataToObserve.postValue(AppStateError(dataError))
                    }
                }
            }
        }
    }
}