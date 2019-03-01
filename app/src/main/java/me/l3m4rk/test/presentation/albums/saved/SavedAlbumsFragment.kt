package me.l3m4rk.test.presentation.albums.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_saved_albums.*
import kotlinx.android.synthetic.main.progress.*
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.albums.top.AlbumsAdapter
import me.l3m4rk.test.presentation.albums.top.TopAlbumsFragmentDirections
import me.l3m4rk.test.presentation.common.ViewState
import javax.inject.Inject

class SavedAlbumsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: SavedAlbumsViewModel

    @Inject
    lateinit var albumsAdapter: AlbumsAdapter

    private var snackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.uiState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> {
                    progress.visibility = View.GONE
                    if (it.data.isEmpty()) {
                        emptyView.visibility = VISIBLE
                        emptyView.text = getString(R.string.saved_albums_no_data)
                    } else {
                        albumsList.visibility = VISIBLE
                        albumsAdapter.submitList(it.data)
                    }
                }
                is ViewState.Progress -> {
                    progress.visibility = View.VISIBLE
                    albumsList.visibility = GONE
                    emptyView.visibility = GONE
                }
                is ViewState.Error -> {
                    progress.visibility = View.GONE
                    showError(it.message)
                }
                is ViewState.Initial -> {
                    progress.visibility = View.GONE
                }
            }
        })

        setupList()
    }

    private fun setupList() {
        albumsList.adapter = albumsAdapter
        albumsAdapter.itemClick = {
            TopAlbumsFragmentDirections.albumDetailsFragment(
                album = it.name,
                artist = it.artist ?: ""
            ).also { findNavController().navigate(it) }
        }
        albumsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        albumsList.itemAnimator = DefaultItemAnimator()
        albumsList.layoutManager = LinearLayoutManager(context)
    }

    private fun showError(message: String) {
        snackbar = Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.action_retry) {
                viewModel.loadAlbums()
            }
        snackbar?.show()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAlbums()
    }

    override fun onStop() {
        super.onStop()
        snackbar?.dismiss()
    }

}
