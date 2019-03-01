package me.l3m4rk.test.presentation.albums.top

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_top_albums.*
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.models.AlbumVO
import javax.inject.Inject

class TopAlbumsFragment : DaggerFragment() {

    private val args: TopAlbumsFragmentArgs by navArgs()
    @Inject
    lateinit var viewModel: TopAlbumsViewModel

    @Inject
    lateinit var albumsAdapter: AlbumsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadTopAlbums(args.name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_top_albums, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.screenState.observe(viewLifecycleOwner, Observer {
            handleScreenState(it)
        })

        setupList()

    }

    private fun handleScreenState(state: ViewState<List<AlbumVO>>) {
        when (state) {
            is ViewState.Success -> {
                progress.visibility = GONE
                albumsList.visibility = VISIBLE
                albumsAdapter.submitList(state.data)

            }
            is ViewState.Error -> {
                progress.visibility = GONE
                showError(state.message)
            }
            is ViewState.Progress -> {
                progress.visibility = VISIBLE
                albumsList.visibility = GONE
            }
        }
    }

    private fun showError(message: String) {
        Snackbar.make(root, message, Snackbar.LENGTH_INDEFINITE).setAction(R.string.action_retry) {
            viewModel.loadTopAlbums(args.name)
        }.show()
    }

    private fun setupList() {
        albumsList.adapter = albumsAdapter
        albumsAdapter.itemClick = {
            TopAlbumsFragmentDirections.albumDetailsFragment(
                album = it.name,
                artist = args.name
            ).also { findNavController().navigate(it) }
        }
        albumsList.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        albumsList.itemAnimator = DefaultItemAnimator()
        albumsList.layoutManager = LinearLayoutManager(context)
    }

}
