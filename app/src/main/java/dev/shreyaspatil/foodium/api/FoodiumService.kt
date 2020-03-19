package dev.shreyaspatil.foodium.api

import dev.shreyaspatil.foodium.api.FoodiumService.Companion.FOODIUM_API_URL
import dev.shreyaspatil.foodium.utils.PostsResponse
import retrofit2.http.GET

/**
 * Service to fetch Foodium posts using dummy end point [FOODIUM_API_URL].
 */
interface FoodiumService {

    @GET("/DummyFoodiumApi/api/posts/")
    suspend fun getPosts(): PostsResponse

    companion object {
        const val FOODIUM_API_URL = "https://patilshreyas.github.io/"
    }
}