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
import me.l3m4rk.test.presentation.albums.top.TopAlbumsViewModel
import me.l3m4rk.test.presentation.common.ErrorMessageFactory

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
            messageFactory: ErrorMessageFactory,
            fragment: SearchArtistsFragment
        ): SearchArtistsViewModel {
            return fragment.viewModel { SearchArtistsViewModel(lastFmApi, messageFactory) }
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideTopAlbumsViewModel(
            lastFmApi: LastFmApi,
            messageFactory: ErrorMessageFactory,
            fragment: TopAlbumsFragment
        ): TopAlbumsViewModel {
            return fragment.viewModel { TopAlbumsViewModel(lastFmApi, messageFactory) }
        }
    }

}