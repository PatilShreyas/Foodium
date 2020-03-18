package dev.shreyaspatil.foodium.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dev.shreyaspatil.foodium.ViewModelProviderFactory

@Module
interface ViewModelFactoryModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory
}