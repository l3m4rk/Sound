package me.l3m4rk.test.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import me.l3m4rk.test.data.db.AlbumDatabase
import javax.inject.Singleton

private const val DB_NAME = "albums.db"

@Module
class AppModule {

    @Provides
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    fun provideDatabase(context: Context): AlbumDatabase {
        return Room.databaseBuilder(context, AlbumDatabase::class.java, DB_NAME).build()
    }

}