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
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

typealias PostListViewState = ViewState<List<Post>>

@ExperimentalCoroutinesApi
@Singleton
class PostsRepository @Inject constructor(
    val postsDao: PostsDao,
    val foodiumService: FoodiumService
) {

    fun getAllPosts(): Flow<PostListViewState> =
        flow {
            emit(Loading<List<Post>>())

            val dbPosts = fetchFromDatabase().first()
            emit(Success<List<Post>>(dbPosts))
            try {
                val apiResponse = fetchFromRemote()
                val remotePosts = apiResponse.body()
                if (apiResponse.isSuccessful && remotePosts != null) {
                    saveRemoteData(remotePosts)
                } else {
                    emit(Error<List<Post>>(apiResponse.message()))
                }
                println("API Response = $apiResponse")
            } catch (e: Exception) {
                emit(Error<List<Post>>("Failed to load from network!"))
                e.printStackTrace()
            }

            emitAll(fetchFromDatabase().map { Success<List<Post>>(it) })
        }.flowOn(Dispatchers.IO)


    private fun saveRemoteData(posts: List<Post>) = postsDao.insertPosts(posts)
    private fun fetchFromDatabase() = postsDao.getAllPosts()
    private suspend fun fetchFromRemote(): PostsResponse = foodiumService.getPosts()
}