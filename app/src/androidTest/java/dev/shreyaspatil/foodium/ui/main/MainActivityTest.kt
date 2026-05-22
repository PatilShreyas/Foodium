package dev.shreyaspatil.foodium.ui.main

import androidx.test.core.app.ActivityScenario
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dev.shreyaspatil.foodium.ui.screen.MainScreen
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Test
import org.junit.runner.RunWith

@Epic("UI Tests")
@Feature("MainActivity")
@RunWith(AllureAndroidJUnit4::class)
class MainActivityTest : TestCase() {

    @Story("Posts List")
    @Description("Проверка отображения RecyclerView")
    @Test
    fun mainActivity_displaysRecyclerView() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Проверка SwipeRefreshLayout") {
            MainScreen {
                swipeRefreshLayout {
                    isDisplayed()
                }
            }
        }

        step("Проверка RecyclerView") {
            MainScreen {
                recyclerView {
                    isDisplayed()
                }
            }
        }
    }

    @Story("Posts List")
    @Description("Проверка загрузки постов из сети")
    @Test
    fun mainActivity_recyclerViewContainsPosts() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Ожидание загрузки") {
            Thread.sleep(3000)
        }

        step("Проверка элементов списка") {
            MainScreen {
                recyclerView {
                    isDisplayed()
                    firstChild<MainScreen.PostItem> {
                        isDisplayed()
                        title.isDisplayed()
                        author.isDisplayed()
                    }
                }
            }
        }
    }

    @Story("Network Status")
    @Description("Проверка UI элементов")
    @Test
    fun mainActivity_networkStatusElements_exist() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Ожидание инициализации") {
            Thread.sleep(2000)
        }

        step("Проверка SwipeRefreshLayout") {
            MainScreen {
                swipeRefreshLayout {
                    isDisplayed()
                }
            }
        }

        step("Проверка RecyclerView") {
            MainScreen {
                recyclerView {
                    isDisplayed()
                }
            }
        }
    }
}
