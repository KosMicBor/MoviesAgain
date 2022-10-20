package kosmicbor.moviesagain.ui.mainscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kosmicbor.moviesagain.R
import kosmicbor.moviesagain.data.dataobjects.ListMovie
import kosmicbor.moviesagain.databinding.ItemListMainScreenBinding
import kosmicbor.moviesagain.utils.DiffUtilMainScreenList

class MainScreenListAdapter : RecyclerView.Adapter<MainScreenListAdapter.MainScreenViewHolder>() {

    private val moviesList = mutableListOf<ListMovie>()

    inner class MainScreenViewHolder(binding: ItemListMainScreenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title = binding.itemMainListTitle
        val cover = binding.itemMainListCover
        val releaseDate = binding.itemMainListReleaseDate
        val votes = binding.itemMainListVotes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        val itemBinding = ItemListMainScreenBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MainScreenViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        with(holder) {

            title.text = moviesList[position].originalTitle
            if (moviesList[position].posterPath != null) {
                cover.load("https://image.tmdb.org/t/p/w500${moviesList[position].posterPath}") {
                    placeholder(R.drawable.ic_baseline_image_24)
                    error(R.drawable.ic_baseline_broken_image_24)
                }
            } else {
                cover.load(R.drawable.ic_baseline_broken_image_24)
            }
            releaseDate.text = moviesList[position].releaseDate
            votes.text = moviesList[position].voteAverage.toString()
        }
    }

    override fun getItemCount(): Int = moviesList.size

    fun updateData(newList: List<ListMovie>) {

        val diffUtil = DiffUtilMainScreenList(moviesList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        moviesList.clear()
        moviesList.addAll(newList)
        result.dispatchUpdatesTo(this@MainScreenListAdapter)
    }
}