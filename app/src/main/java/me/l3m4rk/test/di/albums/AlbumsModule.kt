package me.l3m4rk.test.di.albums

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.android.ContributesAndroidInjector
import me.l3m4rk.test.data.repositories.AlbumsRepository
import me.l3m4rk.test.data.repositories.AlbumsRepositoryImpl
import me.l3m4rk.test.di.common.viewModel
import me.l3m4rk.test.domain.albums.details.GetAlbumDetailsUseCase
import me.l3m4rk.test.domain.albums.details.GetAlbumDetailsUseCaseImpl
import me.l3m4rk.test.domain.albums.search.SearchArtistUseCaseImpl
import me.l3m4rk.test.domain.albums.search.SearchArtistsUseCase
import me.l3m4rk.test.domain.albums.top.GetTopAlbumsUseCase
import me.l3m4rk.test.domain.albums.top.GetTopAlbumsUseCaseImpl
import me.l3m4rk.test.presentation.albums.details.AlbumDetailsFragment
import me.l3m4rk.test.presentation.albums.details.AlbumDetailsViewModel
import me.l3m4rk.test.presentation.albums.details.SaveAlbumUseCase
import me.l3m4rk.test.presentation.albums.details.SaveAlbumUseCaseImpl
import me.l3m4rk.test.presentation.albums.search.SearchArtistsFragment
import me.l3m4rk.test.presentation.albums.search.SearchArtistsViewModel
import me.l3m4rk.test.presentation.albums.top.TopAlbumsFragment
import me.l3m4rk.test.presentation.albums.top.TopAlbumsViewModel

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

    @Binds
    abstract fun bindSearchArtistsUseCase(impl: SearchArtistUseCaseImpl): SearchArtistsUseCase

    @Binds
    abstract fun bindTopAlbumsUseCase(useCase: GetTopAlbumsUseCaseImpl): GetTopAlbumsUseCase

    @Binds
    abstract fun bindAlbumDetailsUseCase(useCase: GetAlbumDetailsUseCaseImpl): GetAlbumDetailsUseCase

    @Binds
    abstract fun bindAlbumSaveUseCase(useCase: SaveAlbumUseCaseImpl): SaveAlbumUseCase

    @Module
    companion object {

        @Provides
        @Reusable
        @JvmStatic
        fun provideSearchViewModel(
            searchArtistsUseCase: SearchArtistsUseCase,
            fragment: SearchArtistsFragment
        ): SearchArtistsViewModel {
            return fragment.viewModel { SearchArtistsViewModel(searchArtistsUseCase) }
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideTopAlbumsViewModel(
            useCase: GetTopAlbumsUseCase,
            fragment: TopAlbumsFragment
        ): TopAlbumsViewModel {
            return fragment.viewModel { TopAlbumsViewModel(useCase) }
        }

        @Provides
        @Reusable
        @JvmStatic
        fun provideAlbumDetailsViewModel(
            saveAlbumUseCase: SaveAlbumUseCase,
            getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
            fragment: AlbumDetailsFragment
        ): AlbumDetailsViewModel {
            return fragment.viewModel { AlbumDetailsViewModel(saveAlbumUseCase, getAlbumDetailsUseCase) }
        }
    }

}