package me.l3m4rk.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.l3m4rk.test.di.albums.AlbumsModule
import me.l3m4rk.test.presentation.MainActivity

@Module
abstract class AndroidBuilders {

    @ContributesAndroidInjector(modules = [AlbumsModule::class])
    abstract fun bindMainActvivity(): MainActivity

}
