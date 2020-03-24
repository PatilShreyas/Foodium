package dev.shreyaspatil.foodium.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import dev.shreyaspatil.foodium.data.local.FoodiumPostsDatabase
import javax.inject.Singleton

@Module
class FoodiumDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application) = FoodiumPostsDatabase.getInstance(application)

    @Singleton
    @Provides
    fun providePostsDao(database: FoodiumPostsDatabase) = database.getPostsDao()
}