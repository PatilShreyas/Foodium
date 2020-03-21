package dev.shreyaspatil.foodium.database

import dev.shreyaspatil.foodium.api.FoodiumService
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.ui.State
import dev.shreyaspatil.foodium.utils.PostsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Singleton repository for fetching data from remote and storing it in database
 * for offline capability. This is Single source of data.
 */
@ExperimentalCoroutinesApi
@Singleton
class PostsRepository @Inject constructor(
    private val postsDao: PostsDao,
    private val foodiumService: FoodiumService
) {

    /**
     * Fetched the posts from network and stored it in database. At the end, data from persistence
     * storage is fetched and emitted.
     */
    fun getAllPosts() = flow {

        // Emit Loading State
        emit(State.loading<List<Post>>())

        try {
            // Fetch latest posts from remote
            val apiResponse = fetchFromRemote()

            // Parse body
            val remotePosts = apiResponse.body()

            // Check for response validation
            if (apiResponse.isSuccessful && remotePosts != null) {
                // Save posts into the persistence storage
                saveRemoteData(remotePosts)
            } else {
                // Something went wrong! Emit Error state.
                emit(State.error<List<Post>>(apiResponse.message()))
            }
        } catch (e: Exception) {
            // Exception occurred! Emit error
            emit(State.error<List<Post>>("Network error! Can't get latest posts."))
            e.printStackTrace()
        }

        // Retrieve posts from persistence storage and emit
        emitAll(fetchFromDatabase().map {
            if (!it.isNullOrEmpty()) {
                // Posts retrieved. Emit Success state
                State.success<List<Post>>(it)
            } else {
                // No posts found in persistence storage.
                State.error<List<Post>>("Can't get latest posts!")
            }
        })
    }.flowOn(Dispatchers.IO)

    /**
     * Saves [posts] retrieved from remote into the persistence storage.
     * @param posts List of [Post] which is loaded from remote end point.
     */
    private fun saveRemoteData(posts: List<Post>) = postsDao.insertPosts(posts)

    /**
     * Retrieves all posts from persistence storage.
     */
    private fun fetchFromDatabase() = postsDao.getAllPosts()

    /**
     * Fetches [PostsResponse] from the remote end point. See [FoodiumService.getPosts]
     */
    private suspend fun fetchFromRemote(): PostsResponse = foodiumService.getPosts()
}