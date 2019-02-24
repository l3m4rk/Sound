package me.l3m4rk.test.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.l3m4rk.test.SoundApp
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    AndroidSupportInjectionModule::class,
    AndroidBuilders::class,
    NetworkModule::class
])
interface AppComponent : AndroidInjector<SoundApp> {


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

}