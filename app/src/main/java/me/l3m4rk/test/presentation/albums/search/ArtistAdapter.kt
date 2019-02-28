package me.l3m4rk.test.presentation.albums.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_artist.view.*
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.models.ArtistVO
import javax.inject.Inject

class ArtistAdapter
@Inject constructor() : ListAdapter<ArtistVO, ArtistViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArtistVO>() {
            override fun areItemsTheSame(oldItem: ArtistVO, newItem: ArtistVO) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: ArtistVO, newItem: ArtistVO) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_artist, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val item = getItem(position)

        holder.nameView.text = item.name
        holder.imageView.contentDescription = item.name
        Glide.with(holder.imageView).load(item.imageUrl).placeholder(R.color.progress).centerCrop().into(holder.imageView)
    }
}

class ArtistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nameView: TextView = view.name
    val imageView: ImageView = view.image
}
