package dev.shreyaspatil.foodium.model

import dev.shreyaspatil.foodium.data.repository.Resource
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Epic("Unit Tests")
@Feature("State")
@RunWith(JUnit4::class)
class StateTest {

    @Story("State.fromResource")
    @Description("Проверка конвертации Success ресурса")
    @Test
    fun fromResource_withSuccess_returnsSuccessState() {
        val testData = "test data"
        val resource = Resource.Success(testData)

        val state = State.fromResource(resource)

        assertTrue(state is State.Success)
        assertEquals(testData, (state as State.Success).data)
    }

    @Story("State.fromResource")
    @Description("Проверка конвертации Failed ресурса")
    @Test
    fun fromResource_withFailed_returnsErrorState() {
        val errorMessage = "error message"
        val resource = Resource.Failed<String>(errorMessage)

        val state = State.fromResource(resource)

        assertTrue(state is State.Error)
        assertEquals(errorMessage, (state as State.Error).message)
    }

    @Story("State Flags")
    @Description("Проверка флага isLoading")
    @Test
    fun isLoading_returnsTrueForLoadingState() {
        val loadingState: State<String> = State.loading()

        assertTrue(loadingState.isLoading())
        assertFalse(loadingState.isSuccessful())
        assertFalse(loadingState.isFailed())
    }

    @Story("State Flags")
    @Description("Проверка флага isSuccessful")
    @Test
    fun isSuccessful_returnsTrueForSuccessState() {
        val successState: State<String> = State.success("data")

        assertFalse(successState.isLoading())
        assertTrue(successState.isSuccessful())
        assertFalse(successState.isFailed())
    }

    @Story("State Flags")
    @Description("Проверка флага isFailed")
    @Test
    fun isFailed_returnsTrueForErrorState() {
        val errorState: State<String> = State.error("error")

        assertFalse(errorState.isLoading())
        assertFalse(errorState.isSuccessful())
        assertTrue(errorState.isFailed())
    }
}
