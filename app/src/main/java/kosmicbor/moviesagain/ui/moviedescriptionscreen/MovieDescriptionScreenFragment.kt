package kosmicbor.moviesagain.ui.moviedescriptionscreen

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.google.android.material.snackbar.Snackbar
import kosmicbor.moviesagain.R
import kosmicbor.moviesagain.data.dataobjects.Genre
import kosmicbor.moviesagain.data.dataobjects.MovieDefinition
import kosmicbor.moviesagain.databinding.FragmentMovieDescriptionBinding
import kosmicbor.moviesagain.di.MOVIE_DESC_SCREEN_VIEWMODEL_NAME
import kosmicbor.moviesagain.ui.mainscreen.MainScreenFragment.Companion.DEFAULT_LANGUAGE_VALUE
import kosmicbor.moviesagain.ui.mainscreen.MainScreenFragment.Companion.MOVIE_ID_KEY
import kosmicbor.moviesagain.utils.AppState
import kosmicbor.moviesagain.utils.AppStateError
import kosmicbor.moviesagain.utils.AppStateLoading
import kosmicbor.moviesagain.utils.AppStateSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MovieDescriptionScreenFragment : Fragment(R.layout.fragment_movie_description) {

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle): MovieDescriptionScreenFragment {
            val fragment = MovieDescriptionScreenFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentMovieDescriptionBinding by viewBinding(
        FragmentMovieDescriptionBinding::bind
    )
    private val descViewModel: MovieDescriptionScreenViewModel by viewModel(
        named(MOVIE_DESC_SCREEN_VIEWMODEL_NAME)
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt(MOVIE_ID_KEY) ?: throw NullPointerException(
            getString(
                R.string.error_fragment_arguments_is_null_message
            )
        )

        subscribeToLiveData()

        descViewModel.getMovieDescription(movieId, DEFAULT_LANGUAGE_VALUE)
    }

    private fun subscribeToLiveData() {
        descViewModel.liveDataToObserve.observe(viewLifecycleOwner) {
            renderData(it)
        }
    }

    private fun renderData(appState: AppState) {

        when (appState) {

            is AppStateLoading -> {
                showProgress()
            }

            is AppStateSuccess<*> -> {
                showStandardScreen()

                val movie = appState.value as MovieDefinition

                with(binding) {
                    movieDescScreenTitle.text = movie.originalTitle
                    movieDescScreenBackdropImage.load("https://image.tmdb.org/t/p/original${movie.backdropPath}") {
                        placeholder(R.drawable.ic_baseline_image_24)
                        error(R.drawable.ic_baseline_broken_image_24)
                    }

                    movieDescScreenPosterImage.load("https://image.tmdb.org/t/p/original/w500${movie.posterPath}") {
                        placeholder(R.drawable.ic_baseline_image_24)
                        error(R.drawable.ic_baseline_broken_image_24)
                    }

                    Log.d("@@@", "https://image.tmdb.org/t/p/original/w500${movie.posterPath}")

                    movieDescScreenStatus.text = "${movie.status}:"
                    movieDescScreenDate.text = movie.releaseDate
                    movieDescScreenDuration.text = "${movie.runtime}m"
                    movieDescScreenGenres.text = buildGenresStroke(movie.genres)
                    movieDescScreenVotes.text = movie.voteAverage.toString()
                    movieDescScreenOverview.text = movie.overview
                }
            }

            is AppStateError<*> -> {
                val errorMessage = appState.error as String

                showError(errorMessage)
            }
        }
    }

    private fun buildGenresStroke(genresList: List<Genre>): String {
        val sb = StringBuilder()

        genresList.forEach {
            sb.append(it.name)
            if (genresList.indexOf(it) != genresList.lastIndex) {
                sb.append(", ")
            }
        }

        return sb.toString()
    }

    private fun showProgress() {
        binding.movieDescScreenProgressbarContainer.visibility = View.VISIBLE
    }

    private fun showStandardScreen() {
        binding.movieDescScreenProgressbarContainer.visibility = View.GONE
    }

    private fun showError(message: String) {

        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}