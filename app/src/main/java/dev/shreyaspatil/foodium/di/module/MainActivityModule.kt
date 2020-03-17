package dev.shreyaspatil.foodium.di.module

import dagger.Module
import dagger.Provides
import dev.shreyaspatil.foodium.ui.adapter.PostListAdapter

@Module
class MainActivityModule {

    @Provides
    fun providePostListAdapter() = PostListAdapter()
}
