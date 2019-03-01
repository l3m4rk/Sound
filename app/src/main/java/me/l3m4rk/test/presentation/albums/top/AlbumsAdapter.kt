package me.l3m4rk.test.presentation.albums.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_album.view.*
import me.l3m4rk.test.R
import javax.inject.Inject

class AlbumsAdapter
@Inject constructor() : ListAdapter<AlbumVO, AlbumViewHolder>(DIFF_CALLBACK) {

    var itemClick: ((AlbumVO) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlbumVO>() {
            override fun areItemsTheSame(oldItem: AlbumVO, newItem: AlbumVO): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: AlbumVO, newItem: AlbumVO): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = getItem(position)

        with(holder.itemView.albumName) {
            text = item.name
            contentDescription = item.name
        }

        Glide.with(holder.itemView.albumImage)
            .load(item.imageUrl)
            .placeholder(R.color.progress)
            .centerCrop()
            .into(holder.itemView.albumImage)

        holder.itemView.setOnClickListener { itemClick?.invoke(item) }
    }
}

class AlbumViewHolder(view: View) : RecyclerView.ViewHolder(view)
