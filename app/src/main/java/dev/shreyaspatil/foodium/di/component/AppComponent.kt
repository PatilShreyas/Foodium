package dev.shreyaspatil.foodium.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.shreyaspatil.foodium.FoodiumApp
import dev.shreyaspatil.foodium.di.module.ActivitiesModule
import dev.shreyaspatil.foodium.di.module.FoodiumApiModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FoodiumApiModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent : AndroidInjector<FoodiumApp> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun create(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: FoodiumApp)
}