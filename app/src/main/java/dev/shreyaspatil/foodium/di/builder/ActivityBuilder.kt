package dev.shreyaspatil.foodium.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.shreyaspatil.foodium.ui.details.PostDetailsActivity
import dev.shreyaspatil.foodium.ui.main.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@Module
abstract class ActivityBuilder {

    @ExperimentalCoroutinesApi
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ExperimentalCoroutinesApi
    @ContributesAndroidInjector
    abstract fun bindPostDetailsActivity(): PostDetailsActivity

}
