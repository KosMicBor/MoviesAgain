package kosmicbor.moviesagain.utils

sealed class DataState
data class DataStateSuccess<T>(val value: T) : DataState()
data class DataStateError<T>(val error: T) : DataState()