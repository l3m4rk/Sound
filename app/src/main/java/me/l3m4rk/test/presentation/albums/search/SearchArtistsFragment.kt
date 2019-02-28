package me.l3m4rk.test.presentation.albums.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_search_artists.*
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.common.hideKeyboard
import javax.inject.Inject

class SearchArtistsFragment : DaggerFragment() {

    @Inject
    lateinit var viewModel: SearchArtistsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_artists, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //initial state
        progress.visibility = GONE

        searchButton.setOnClickListener {
            viewModel.searchArtists(searchInput.text.toString())
            it.hideKeyboard()
        }

        

    }

}
