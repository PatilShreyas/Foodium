package dev.shreyaspatil.foodium.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.shreyaspatil.foodium.data.repository.PostsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * ViewModel for [PostDetailsActivity]
 */
@ExperimentalCoroutinesApi
class PostDetailsViewModel @Inject constructor(private val postsRepository: PostsRepository) :
    ViewModel() {

    fun getPost(id: Int) = postsRepository.getPostById(id).asLiveData()
}