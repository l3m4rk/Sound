package me.l3m4rk.test.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: Application) = app.applicationContext

}