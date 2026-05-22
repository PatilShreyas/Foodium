package dev.shreyaspatil.foodium.ui.details

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dev.shreyaspatil.foodium.ui.main.MainActivity
import dev.shreyaspatil.foodium.ui.screen.PostDetailsScreen
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Test
import org.junit.runner.RunWith

@Epic("UI Tests")
@Feature("PostDetailsActivity")
@RunWith(AllureAndroidJUnit4::class)
class PostDetailsActivityTest : TestCase() {

    companion object {
        private const val KEY_POST_ID = "postId"
        private const val TEST_POST_ID = 1
    }

    private fun createIntentWithPostId(postId: Int): Intent {
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        return Intent(context, PostDetailsActivity::class.java).apply {
            putExtra(KEY_POST_ID, postId)
        }
    }

    private fun ensurePostsLoaded() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(4000)
        scenario.close()
    }

    @Story("Post Details")
    @Description("Проверка отображения контента поста")
    @Test
    fun postDetailsActivity_withValidPostId_displaysContent() = run {
        step("Загрузка постов через MainActivity") {
            ensurePostsLoaded()
        }

        step("Запуск PostDetailsActivity") {
            val intent = createIntentWithPostId(TEST_POST_ID)
            ActivityScenario.launch<PostDetailsActivity>(intent)
        }

        step("Ожидание загрузки из БД") {
            Thread.sleep(2000)
        }

        step("Проверка toolbar") {
            PostDetailsScreen {
                toolbar {
                    isDisplayed()
                }
            }
        }

        step("Проверка заголовка") {
            PostDetailsScreen {
                postTitle {
                    isDisplayed()
                }
            }
        }

        step("Проверка автора") {
            PostDetailsScreen {
                postAuthor {
                    isDisplayed()
                }
            }
        }

        step("Проверка текста поста") {
            PostDetailsScreen {
                postBody {
                    isDisplayed()
                }
            }
        }
    }

    @Story("Post Details")
    @Description("Проверка всех текстовых полей")
    @Test
    fun postDetailsActivity_displaysAllTextFields() = run {
        step("Загрузка постов") {
            ensurePostsLoaded()
        }

        step("Запуск PostDetailsActivity") {
            val intent = createIntentWithPostId(TEST_POST_ID)
            ActivityScenario.launch<PostDetailsActivity>(intent)
        }

        step("Ожидание загрузки") {
            Thread.sleep(2000)
        }

        step("Проверка всех полей") {
            PostDetailsScreen {
                postTitle.isDisplayed()
                postAuthor.isDisplayed()
                postBody.isDisplayed()
            }
        }
    }
}
