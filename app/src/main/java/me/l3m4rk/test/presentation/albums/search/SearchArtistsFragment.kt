package me.l3m4rk.test.presentation.albums.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_artists.*
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.common.ViewState
import me.l3m4rk.test.presentation.common.hideKeyboard
import javax.inject.Inject

class SearchArtistsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: SearchArtistsViewModel
    @Inject
    lateinit var artistsAdapter: ArtistAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchButton.setOnClickListener {
            viewModel.searchArtists(searchInput.text.toString())
            it.hideKeyboard()
        }

        viewModel.uiState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is ViewState.Success -> {
                    progress.visibility = GONE
                    if (it.data.isEmpty()) {
                        showError(getString(R.string.search_artists_no_data))
                    } else {
                        artistsList.visibility = VISIBLE
                        artistsAdapter.submitList(it.data)
                    }
                }
                is ViewState.Progress -> {
                    progress.visibility = VISIBLE
                    artistsList.visibility = GONE
                    messageView.visibility = GONE
                }
                is ViewState.Error -> {
                    progress.visibility = GONE
                    showError(it.message)
                }
                is ViewState.Initial -> {
                    progress.visibility = GONE
                }
            }
        })

        setupList()
    }

    private fun showError(message: String) {
        //TODO add error view
        //TODO add retry functionality
        messageView.visibility = VISIBLE
        messageView.text = message
    }

    private fun setupList() {
        artistsList.layoutManager = LinearLayoutManager(context)
        artistsList.itemAnimator = DefaultItemAnimator()
        artistsAdapter.itemClick = {
            val action = SearchArtistsFragmentDirections.actionTopAlbums(it.name)
            findNavController().navigate(action)
        }
        artistsList.adapter = artistsAdapter
    }

}
