package dev.shreyaspatil.foodium.api

import dev.shreyaspatil.foodium.model.Post
import retrofit2.Response
import retrofit2.http.GET

typealias PostsResponse = Response<List<Post>>

interface FoodiumService {

    @GET("/DummyFoodiumApi/api/posts/")
    suspend fun getPosts(): PostsResponse

    companion object {
        const val FOODIUM_API_URL = "https://patilshreyas.github.io/"
    }
}