package kosmicbor.moviesagain.ui.mainscreen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import kosmicbor.moviesagain.R
import kosmicbor.moviesagain.data.dataobjects.ListMovie
import kosmicbor.moviesagain.databinding.FragmentMainScreenBinding
import kosmicbor.moviesagain.di.MAIN_SCREEN_VIEWMODEL_NAME
import kosmicbor.moviesagain.utils.AppState
import kosmicbor.moviesagain.utils.AppStateError
import kosmicbor.moviesagain.utils.AppStateLoading
import kosmicbor.moviesagain.utils.AppStateSuccess
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class MainScreenFragment : Fragment(R.layout.fragment_main_screen) {

    companion object {
        private const val DEFAULT_LANGUAGE_VALUE = "en-US"
        private const val DEFAULT_PAGE_VALUE = "1"
        private const val LAYOUT_MANAGER_SPAN_COUNT = 2
        private const val ZERO_VAL = 0
        private const val LOAD_MORE_DIFFERENCE = 3

    }

    private var currentUpdatePage = 1
    private val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)
    private val mainListAdapter = MainScreenListAdapter()
    private val mainViewModel: MainScreenViewModel by viewModel(named(MAIN_SCREEN_VIEWMODEL_NAME))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            mainViewModel.getMoviesList(DEFAULT_LANGUAGE_VALUE, DEFAULT_PAGE_VALUE)
        }

        initRecyclerView()
        subscribeToLiveData()
    }

    private fun initRecyclerView() {
        binding.mainScreenRecyclerView.apply {
            layoutManager =
                GridLayoutManager(
                    context,
                    LAYOUT_MANAGER_SPAN_COUNT,
                    GridLayoutManager.VERTICAL,
                    false
                )
            adapter = mainListAdapter
            initOnScrollListener(this@apply)
        }
    }

    private fun initOnScrollListener(recyclerView: RecyclerView) {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var pastVisibleItems: Int = ZERO_VAL
            var visibleItemCount: Int = ZERO_VAL
            var totalItemCount: Int = ZERO_VAL
            var previousTotalCount: Int = ZERO_VAL
            val layoutManager = recyclerView.layoutManager as GridLayoutManager

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > ZERO_VAL) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisibleItems = layoutManager.findLastVisibleItemPosition()

                    if (visibleItemCount + pastVisibleItems > totalItemCount - LOAD_MORE_DIFFERENCE
                        && totalItemCount > previousTotalCount) {

                        currentUpdatePage++
                        previousTotalCount = totalItemCount
                        mainViewModel.getMoviesList(
                            DEFAULT_LANGUAGE_VALUE,
                            currentUpdatePage.toString()
                        )
                    }

                }
            }
        })
    }

    private fun subscribeToLiveData() {
        mainViewModel.dataToObserve.observe(viewLifecycleOwner) {
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

                val moviesList = appState.value as List<ListMovie>
                mainListAdapter.updateData(moviesList)
            }

            is AppStateError<*> -> {
                val errorMessage = appState.error as String

                showError(errorMessage)
            }
        }
    }

    private fun showProgress() {
        binding.mainProgressbarContainer.visibility = View.VISIBLE
    }

    private fun showStandardScreen() {
        binding.mainProgressbarContainer.visibility = View.GONE
    }

    private fun showError(message: String) {

        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry_string) {
                mainViewModel.getMoviesList(DEFAULT_LANGUAGE_VALUE, DEFAULT_PAGE_VALUE)
            }
            .show()
    }
}