package kosmicbor.moviesagain.utils

sealed class AppState
data class AppStateSuccess<T>(val value: T) : AppState()
data class AppStateError<T>(val error: T) : AppState()
object AppStateLoading : AppState()