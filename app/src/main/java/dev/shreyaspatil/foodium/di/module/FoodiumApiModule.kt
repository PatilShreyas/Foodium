package dev.shreyaspatil.foodium.di.module

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dev.shreyaspatil.foodium.data.remote.api.FoodiumService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class FoodiumApiModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): FoodiumService = Retrofit.Builder()
        .baseUrl(FoodiumService.FOODIUM_API_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .build()
        .create(FoodiumService::class.java)

}
