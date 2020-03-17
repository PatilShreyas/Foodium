package dev.shreyaspatil.foodium.di.module

import dagger.Module
import dagger.Provides
import dev.shreyaspatil.foodium.api.FoodiumService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class FoodiumApiModule {

    @Provides
    fun provideRetrofitService() = Retrofit.Builder()
        .baseUrl(FoodiumService.FOODIUM_API_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(FoodiumService::class.java)

}
