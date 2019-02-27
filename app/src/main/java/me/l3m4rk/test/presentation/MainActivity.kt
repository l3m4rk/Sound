package me.l3m4rk.test.presentation

import android.net.Uri
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import me.l3m4rk.test.R
import me.l3m4rk.test.presentation.albums.saved.SavedAlbumsFragment
import me.l3m4rk.test.presentation.albums.search.SearchArtistsFragment
import me.l3m4rk.test.presentation.albums.top.TopAlbumsFragment
import timber.log.Timber

class MainActivity : DaggerAppCompatActivity(),
    SavedAlbumsFragment.OnFragmentInteractionListener,
    SearchArtistsFragment.OnFragmentInteractionListener,
    TopAlbumsFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, SavedAlbumsFragment.newInstance())
                .commit()
        }
    }

    override fun onArtistPicked() {

    }

    override fun onFragmentInteraction(uri: Uri) {

    }

    override fun onAlbumSelected() {
        Timber.i("onAlbumSelected clicked!")
    }
}
