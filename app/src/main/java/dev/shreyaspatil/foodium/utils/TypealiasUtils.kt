package dev.shreyaspatil.foodium.utils

import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.State
import retrofit2.Response


typealias PostsResponse = Response<List<Post>>

typealias PostListState = State<List<Post>>