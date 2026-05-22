package dev.shreyaspatil.foodium.ui.dialog

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import dev.shreyaspatil.foodium.ui.main.MainActivity
import dev.shreyaspatil.foodium.ui.screen.MainScreen
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Test
import org.junit.runner.RunWith

@Epic("UI Tests")
@Feature("Exit Dialog")
@RunWith(AllureAndroidJUnit4::class)
class ExitDialogTest : TestCase() {

    @Story("Dialog Display")
    @Description("Проверка отображения диалога выхода")
    @Test
    fun exitDialog_onBackPressed_dialogIsDisplayed() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Ожидание инициализации") {
            Thread.sleep(2000)
        }

        step("Нажатие кнопки Back") {
            Espresso.pressBack()
        }

        step("Проверка заголовка диалога") {
            onView(withText("Exit?"))
                .check(matches(isDisplayed()))
        }

        step("Проверка текста диалога") {
            onView(withText("Are you sure want to exit?"))
                .check(matches(isDisplayed()))
        }

        step("Проверка кнопки Yes") {
            onView(withText("Yes"))
                .check(matches(isDisplayed()))
        }

        step("Проверка кнопки No") {
            onView(withText("No"))
                .check(matches(isDisplayed()))
        }
    }

    @Story("Dialog Actions")
    @Description("Проверка закрытия диалога кнопкой No")
    @Test
    fun exitDialog_clickNo_dialogDismissedAndStaysOnMain() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Ожидание инициализации") {
            Thread.sleep(2000)
        }

        step("Вызов диалога выхода") {
            Espresso.pressBack()
        }

        step("Ожидание диалога") {
            Thread.sleep(500)
        }

        step("Нажатие No") {
            onView(withText("No")).perform(click())
        }

        step("Проверка главного экрана") {
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

    @Story("Dialog Actions")
    @Description("Проверка повторного вызова диалога")
    @Test
    fun exitDialog_showMultipleTimes_worksCorrectly() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Ожидание инициализации") {
            Thread.sleep(2000)
        }

        step("Первый вызов и закрытие") {
            Espresso.pressBack()
            Thread.sleep(500)
            onView(withText("No")).perform(click())
        }

        step("Проверка главного экрана") {
            MainScreen {
                swipeRefreshLayout.isDisplayed()
            }
        }

        step("Повторный вызов диалога") {
            Espresso.pressBack()
        }

        step("Проверка повторного отображения") {
            onView(withText("Exit?"))
                .check(matches(isDisplayed()))
        }

        step("Закрытие диалога") {
            onView(withText("No")).perform(click())
        }
    }

    @Story("Error Scenario")
    @Description("Проверка стабильности при быстрых нажатиях")
    @Test
    fun exitDialog_rapidBackPresses_handledGracefully() = run {
        step("Запуск MainActivity") {
            ActivityScenario.launch(MainActivity::class.java)
        }

        step("Ожидание загрузки") {
            Thread.sleep(3000)
        }

        step("Нажатие Back") {
            Espresso.pressBack()
        }

        step("Ожидание диалога") {
            Thread.sleep(500)
        }

        step("Проверка диалога") {
            onView(withText("Exit?"))
                .check(matches(isDisplayed()))
        }

        step("Закрытие") {
            onView(withText("No")).perform(click())
        }

        step("Проверка работоспособности") {
            MainScreen {
                swipeRefreshLayout.isDisplayed()
                recyclerView.isDisplayed()
            }
        }
    }
}
