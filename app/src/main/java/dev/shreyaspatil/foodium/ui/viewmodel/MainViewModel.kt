package dev.shreyaspatil.foodium.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.shreyaspatil.foodium.database.PostsRepository
import dev.shreyaspatil.foodium.utils.PostListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for [dev.shreyaspatil.foodium.ui.activity.MainActivity]
 */
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(private val postsRepository: PostsRepository) :
    ViewModel() {

    private val _postsLiveData = MutableLiveData<PostListState>()

    val postsLiveData: LiveData<PostListState>
        get() = _postsLiveData

    fun getPosts() {
        println("CURRENT STATE: IN VM")
        viewModelScope.launch {
            postsRepository.getAllPosts().collect {
                _postsLiveData.value = it
            }
        }
    }

}
