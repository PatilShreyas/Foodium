package dev.shreyaspatil.foodium.api

import dev.shreyaspatil.foodium.model.Post
import retrofit2.Call
import retrofit2.http.GET

typealias PostsResponse = Call<List<Post>>

interface FoodiumService {

    @GET("/posts")
    fun getPosts(): PostsResponse

    companion object {
        const val FOODIUM_API_URL = "https://patilshreyas.github.io/DummyFoodiumApi/api/"
    }
}