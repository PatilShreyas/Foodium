package dev.shreyaspatil.foodium.database

import dev.shreyaspatil.foodium.api.FoodiumService
import dev.shreyaspatil.foodium.api.PostsResponse
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.Error
import dev.shreyaspatil.foodium.ui.Loading
import dev.shreyaspatil.foodium.ui.Success
import dev.shreyaspatil.foodium.ui.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

typealias PostListViewState = ViewState<List<Post>>

@ExperimentalCoroutinesApi
@Singleton
class PostsRepository @Inject constructor(
    private val postsDao: PostsDao,
    private val foodiumService: FoodiumService
) {

    fun getAllPosts() = flow {
        emit(Loading<List<Post>>())

        try {
            val apiResponse = fetchFromRemote()
            val remotePosts = apiResponse.body()
            if (apiResponse.isSuccessful && remotePosts != null) {
                saveRemoteData(remotePosts)
            } else {
                emit(Error<List<Post>>(apiResponse.message()))
            }
        } catch (e: Exception) {
            emit(Error<List<Post>>("Network error! Can't get latest posts."))
            e.printStackTrace()
        }
        emitAll(fetchFromDatabase().map {
            if (!it.isNullOrEmpty()) {
                Success<List<Post>>(it)
            } else {
                Error<List<Post>>("No posts!")
            }
        })
        emitAll(fetchFromDatabase().map { Success<List<Post>>(it) })
    }.flowOn(Dispatchers.IO)

    private fun saveRemoteData(posts: List<Post>) = postsDao.insertPosts(posts)
    private fun fetchFromDatabase() = postsDao.getAllPosts()
    private suspend fun fetchFromRemote(): PostsResponse = foodiumService.getPosts()
}