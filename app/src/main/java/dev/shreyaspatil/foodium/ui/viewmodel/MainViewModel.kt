package dev.shreyaspatil.foodium.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dev.shreyaspatil.foodium.database.PostsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(postsRepository: PostsRepository) :
    ViewModel() {

    private val _postsLiveData = postsRepository.getAllPosts().asLiveData()

    val postsLiveData
        get() = _postsLiveData
}