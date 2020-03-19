package dev.shreyaspatil.foodium

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dev.shreyaspatil.foodium.di.component.DaggerAppComponent
import dev.shreyaspatil.foodium.utils.isNight
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

        val mode = if (isNight()) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }

        AppCompatDelegate.setDefaultNightMode(mode)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}