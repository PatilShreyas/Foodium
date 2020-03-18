package dev.shreyaspatil.foodium.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.shreyaspatil.foodium.di.module.MainActivityModule
import dev.shreyaspatil.foodium.ui.activity.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
abstract class ActivityBuilder {

    @ExperimentalCoroutinesApi
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity

}
