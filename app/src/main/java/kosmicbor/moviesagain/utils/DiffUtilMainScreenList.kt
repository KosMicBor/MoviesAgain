package kosmicbor.moviesagain.utils

import androidx.recyclerview.widget.DiffUtil
import kosmicbor.moviesagain.data.dataobjects.ListMovie

class DiffUtilMainScreenList(
    private val oldList: List<ListMovie>,
    private val newList: List<ListMovie>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].originalTitle == newList[newItemPosition].originalTitle &&
                oldList[oldItemPosition].posterPath == newList[newItemPosition].posterPath &&
                oldList[oldItemPosition].releaseDate == newList[newItemPosition].releaseDate &&
                oldList[oldItemPosition].voteAverage == newList[newItemPosition].voteAverage
    }
}