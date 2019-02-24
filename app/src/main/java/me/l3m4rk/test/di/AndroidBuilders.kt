package me.l3m4rk.test.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.l3m4rk.test.presentation.MainActivity

@Module
abstract class AndroidBuilders {

    @ContributesAndroidInjector
    abstract fun bindMainActvivity(): MainActivity

}
