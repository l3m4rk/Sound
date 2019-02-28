package me.l3m4rk.test.di.albums

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.android.ContributesAndroidInjector
import me.l3m4rk.test.data.api.LastFmApi
import me.l3m4rk.test.di.common.viewModel
import me.l3m4rk.test.presentation.albums.search.SearchArtistsFragment
import me.l3m4rk.test.presentation.albums.search.SearchArtistsViewModel
import me.l3m4rk.test.presentation.albums.top.TopAlbumsFragment

@Module
abstract class AlbumsModule {

    @ContributesAndroidInjector
    abstract fun bindSearchArtistsFragment(): SearchArtistsFragment

    @ContributesAndroidInjector
    abstract fun bindTopAlbumsFragment(): TopAlbumsFragment

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun provideSearchViewModel(
            lastFmApi: LastFmApi,
            fragment: SearchArtistsFragment
        ): SearchArtistsViewModel {
            return fragment.viewModel { SearchArtistsViewModel(lastFmApi) }
        }
    }

}