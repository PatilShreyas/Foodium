package dev.shreyaspatil.foodium.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import dev.shreyaspatil.foodium.data.repository.PostRepository
import dev.shreyaspatil.foodium.data.repository.Resource
import dev.shreyaspatil.foodium.model.Post
import dev.shreyaspatil.foodium.model.State
import io.mockk.every
import io.mockk.mockk
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Epic("Unit Tests")
@Feature("MainViewModel")
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MainViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var postRepository: PostRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        postRepository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Story("Initial State")
    @Description("Проверка начального состояния Loading")
    @Test
    fun posts_initialState_isLoading() {
        every { postRepository.getAllPosts() } returns flow { }

        viewModel = MainViewModel(postRepository)

        assertTrue(viewModel.posts.value.isLoading())
    }

    @Story("Load Posts")
    @Description("Проверка успешной загрузки постов")
    @Test
    fun getPosts_successfulResponse_emitsSuccessState() = testDispatcher.runBlockingTest {
        val testPosts = listOf(
            Post(id = 1, title = "Post 1", author = "Author 1", body = "Body 1", imageUrl = null),
            Post(id = 2, title = "Post 2", author = "Author 2", body = "Body 2", imageUrl = null)
        )

        every { postRepository.getAllPosts() } returns flow {
            emit(Resource.Success(testPosts))
        }

        viewModel = MainViewModel(postRepository)

        viewModel.posts.test {
            assertTrue(awaitItem().isLoading())
            viewModel.getPosts()

            val successState = awaitItem()
            assertTrue(successState.isSuccessful())
            assertTrue(successState is State.Success)
            assertEquals(testPosts, (successState as State.Success).data)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Story("Load Posts")
    @Description("Проверка обработки ошибки загрузки")
    @Test
    fun getPosts_failedResponse_emitsErrorState() = testDispatcher.runBlockingTest {
        val errorMessage = "Network error"

        every { postRepository.getAllPosts() } returns flow {
            emit(Resource.Failed<List<Post>>(errorMessage))
        }

        viewModel = MainViewModel(postRepository)

        viewModel.posts.test {
            assertTrue(awaitItem().isLoading())
            viewModel.getPosts()

            val errorState = awaitItem()
            assertTrue(errorState.isFailed())
            assertTrue(errorState is State.Error)
            assertEquals(errorMessage, (errorState as State.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
