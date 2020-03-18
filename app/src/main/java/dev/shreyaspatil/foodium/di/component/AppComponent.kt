package dev.shreyaspatil.foodium.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.shreyaspatil.foodium.FoodiumApp
import dev.shreyaspatil.foodium.di.builder.ActivityBuilder
import dev.shreyaspatil.foodium.di.module.FoodiumApiModule
import dev.shreyaspatil.foodium.di.module.FoodiumDatabaseModule
import dev.shreyaspatil.foodium.di.module.ViewModelFactoryModule
import dev.shreyaspatil.foodium.di.module.ViewModelModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        FoodiumDatabaseModule::class,
        FoodiumApiModule::class,
        ActivityBuilder::class,
        ViewModelFactoryModule::class,
        ViewModelModule::class
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