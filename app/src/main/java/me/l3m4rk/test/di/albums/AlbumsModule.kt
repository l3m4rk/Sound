package me.l3m4rk.test.di.albums

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.android.ContributesAndroidInjector
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.data.repositories.AlbumsRepositoryImpl
import me.l3m4rk.test.di.common.viewModel
import me.l3m4rk.test.presentation.albums.details.AlbumDetailsFragment
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

    @ContributesAndroidInjector
    abstract fun bindAlbumDetailsFragment(): AlbumDetailsFragment

    @Binds
    abstract fun bindRepository(impl: AlbumsRepositoryImpl): AlbumsRepository

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun provideSearchViewModel(
            repository: AlbumsRepository,
            messageFactory: ErrorMessageFactory,
            fragment: SearchArtistsFragment
        ): SearchArtistsViewModel {
            return fragment.viewModel { SearchArtistsViewModel(repository, messageFactory) }
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideTopAlbumsViewModel(
            repository: AlbumsRepository,
            messageFactory: ErrorMessageFactory,
            fragment: TopAlbumsFragment
        ): TopAlbumsViewModel {
            return fragment.viewModel { TopAlbumsViewModel(repository, messageFactory) }
        }
    }

}