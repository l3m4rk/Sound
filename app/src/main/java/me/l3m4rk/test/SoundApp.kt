package me.l3m4rk.test

import com.facebook.stetho.Stetho
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.l3m4rk.test.di.DaggerAppComponent
import timber.log.Timber

class SoundApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
            .builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        }
    }

}