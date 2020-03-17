package dev.shreyaspatil.foodium.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.shreyaspatil.foodium.ui.activity.MainActivity

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity


}
