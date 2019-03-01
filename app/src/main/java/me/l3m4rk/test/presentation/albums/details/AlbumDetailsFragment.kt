package me.l3m4rk.test.presentation.albums.details


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_album_details.*
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.common.ViewState
import javax.inject.Inject


class AlbumDetailsFragment : DaggerFragment() {

    private val args: AlbumDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModel: AlbumDetailsViewModel

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> {
                    progress.visibility = GONE
                    detailsView.visibility = VISIBLE
                    with(it.data) {
                        nameView.text = name
                        artistView.text = artist
                        Glide.with(albumImage)
                            .load(imageUrl)
                            .centerCrop()
                            .into(albumImage)
                        listenersView.text = listeners
                        contentView.text = HtmlCompat.fromHtml(content, HtmlCompat.FROM_HTML_MODE_COMPACT)
                        playedView.text = played

                        if (fromDb) {
                            saveButton.visibility = GONE
                            deleteButton.visibility = VISIBLE
                        } else {
                            saveButton.visibility = VISIBLE
                            deleteButton.visibility = GONE
                        }
                    }
                }
                is ViewState.Error -> {
                    progress.visibility = GONE
                    showError(it.message)
                }
                is ViewState.Progress -> {
                    progress.visibility = VISIBLE
                    detailsView.visibility = GONE
                }
                is ViewState.Initial -> {
                    //
                    detailsView.visibility = GONE
                    deleteButton.visibility = GONE
                }
                is ViewState.Info -> {
                    saveButton.visibility = GONE
                    deleteButton.visibility = VISIBLE
                    snackbar = Snackbar.make(root, it.message, Snackbar.LENGTH_INDEFINITE)
                    snackbar?.show()
                }
            }
        })

        saveButton.setOnClickListener { viewModel.saveAlbum() }
        deleteButton.setOnClickListener { viewModel.deleteAlbum(args.album) }
    }

    private fun showError(message: String) {
        snackbar = Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_retry) { loadAlbumDetails() }
        snackbar?.show()
    }

    override fun onStart() {
        super.onStart()
        loadAlbumDetails()
    }

    private fun loadAlbumDetails() {
        viewModel.loadAlbumDetails(args.artist, args.album)
    }

    override fun onStop() {
        super.onStop()
        snackbar?.dismiss()
    }

}
