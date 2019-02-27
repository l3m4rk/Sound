package me.l3m4rk.test.di.albums

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.l3m4rk.test.presentation.albums.search.SearchArtistsFragment

@Module
abstract class AlbumsModule {

    @ContributesAndroidInjector
    abstract fun bindSearchArtistsFragment(): SearchArtistsFragment

}