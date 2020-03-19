package dev.shreyaspatil.foodium

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dev.shreyaspatil.foodium.di.component.DaggerAppComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FoodiumApp : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
            .create(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}