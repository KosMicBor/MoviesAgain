package kosmicbor.moviesagain.di

import kosmicbor.moviesagain.data.MainRepositoryImpl
import kosmicbor.moviesagain.data.RemoteProviderImpl
import kosmicbor.moviesagain.data.retrofit.RemoteDataSource
import kosmicbor.moviesagain.data.usecases.MainScreenUseCaseImpl
import kosmicbor.moviesagain.data.usecases.MovieDescriptionScreenUsecaseImpl
import kosmicbor.moviesagain.domain.MainRepository
import kosmicbor.moviesagain.domain.RemoteProvider
import kosmicbor.moviesagain.domain.usecases.screenusecases.MainScreenUseCase
import kosmicbor.moviesagain.domain.usecases.screenusecases.MovieDescriptionScreenUseCase
import kosmicbor.moviesagain.ui.mainscreen.MainScreenViewModel
import kosmicbor.moviesagain.ui.moviedescriptionscreen.MovieDescriptionScreenViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.themoviedb.org/3/"
const val REMOTE_PROVIDER_NAME = "REMOTE_PROVIDER_NAME"
const val RETROFIT_NAME = "RETROFIT_NAME"
const val REMOTE_CLIENT_NAME = "REMOTE_CLIENT_NAME"
const val REMOTE_DATA_SOURCE_NAME = "REMOTE_DATA_SOURCE_NAME"
const val MAIN_REPOSITORY_NAME = "REMOTE_DATA_SOURCE_NAME"
const val MAIN_SCREEN_USECASE_NAME = "MAIN_SCREEN_USECASE_NAME"
const val MOVIE_DESC_SCREEN_USECASE_NAME = "MOVIE_DESC_SCREEN_USECASE_NAME"
const val MAIN_SCREEN_VIEWMODEL_NAME = "MAIN_SCREEN_VIEWMODEL_NAME"
const val MOVIE_DESC_SCREEN_VIEWMODEL_NAME = "MOVIE_DESC_SCREEN_VIEWMODEL_NAME"
const val API_KEY = "7ef9cd19cb8b91926a8247355c1c2ff5"

val networkModule = module {
    single<RemoteProvider>(qualifier = named(REMOTE_PROVIDER_NAME)) {
        RemoteProviderImpl(
            remoteAPI = get(qualifier = named(REMOTE_DATA_SOURCE_NAME)),
            apiKey = API_KEY
        )
    }

    single<RemoteDataSource>(qualifier = named(REMOTE_DATA_SOURCE_NAME)) {
        get<Retrofit>(qualifier = named(RETROFIT_NAME)).create(
            RemoteDataSource::class.java
        )
    }

    single(qualifier = named(REMOTE_CLIENT_NAME)) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    single<Retrofit>(qualifier = named(RETROFIT_NAME)) {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get(qualifier = named(REMOTE_CLIENT_NAME)))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<MainRepository>(qualifier = named(MAIN_REPOSITORY_NAME)) {
        MainRepositoryImpl(remoteProvider = get(named(REMOTE_PROVIDER_NAME)))
    }
}

val useCasesModule = module {
    factory<MainScreenUseCase>(qualifier = named(MAIN_SCREEN_USECASE_NAME)) {
        MainScreenUseCaseImpl(repository = get(named(MAIN_REPOSITORY_NAME)))
    }

    factory<MovieDescriptionScreenUseCase>(qualifier = named(MOVIE_DESC_SCREEN_USECASE_NAME)) {
        MovieDescriptionScreenUsecaseImpl(repository = get(named(MAIN_REPOSITORY_NAME)))
    }
}

val viewModelsModule = module {
    viewModel(qualifier = named(MAIN_SCREEN_VIEWMODEL_NAME)) {
        MainScreenViewModel(usecase = get(named(MAIN_SCREEN_USECASE_NAME)))
    }

    viewModel(qualifier = named(MOVIE_DESC_SCREEN_VIEWMODEL_NAME)) {
        MovieDescriptionScreenViewModel(usecase = get(named(MOVIE_DESC_SCREEN_USECASE_NAME)))
    }
}