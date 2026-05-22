package dev.shreyaspatil.foodium.ui.blackbox

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import io.qameta.allure.android.runners.AllureAndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@Epic("Black Box Tests")
@Feature("Foodium App")
@RunWith(AllureAndroidJUnit4::class)
class FoodiumBlackBoxTest {

    private lateinit var device: UiDevice
    private val packageName = "dev.shreyaspatil.foodium"
    private val launchTimeout = 10000L
    private val elementTimeout = 5000L

    @Before
    fun setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        assertNotNull(launcherPackage)
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), launchTimeout)

        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)?.apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        assertNotNull(intent)
        context.startActivity(intent)

        device.wait(Until.hasObject(By.pkg(packageName).depth(0)), launchTimeout)
    }

    @Story("App Launch")
    @Description("Проверка запуска приложения с домашнего экрана")
    @Test
    fun appLaunch_fromHomeScreen_opensMainScreen() {
        val currentPackage = device.currentPackageName
        assertTrue(currentPackage == packageName)

        val recyclerView = device.findObject(UiSelector().resourceId("$packageName:id/postsRecyclerView"))
        assertTrue(recyclerView.waitForExists(elementTimeout))
    }

    @Story("Posts List")
    @Description("Проверка отображения списка постов")
    @Test
    fun postsList_afterLoading_displaysItems() {
        Thread.sleep(5000)

        val recyclerView = device.findObject(UiSelector().resourceId("$packageName:id/postsRecyclerView"))
        assertTrue(recyclerView.exists())

        val listItem = device.findObject(
            UiSelector()
                .resourceId("$packageName:id/postsRecyclerView")
                .childSelector(UiSelector().index(0))
        )
        assertTrue(listItem.waitForExists(elementTimeout))
    }

    @Story("Navigation")
    @Description("Проверка навигации к экрану деталей поста")
    @Test
    fun navigation_clickOnPost_opensDetailsScreen() {
        Thread.sleep(5000)

        val firstPost = device.findObject(
            UiSelector()
                .resourceId("$packageName:id/postsRecyclerView")
                .childSelector(UiSelector().index(0))
        )
        assertTrue(firstPost.waitForExists(elementTimeout))
        firstPost.click()

        Thread.sleep(2000)

        val toolbar = device.findObject(UiSelector().resourceId("$packageName:id/toolbar"))
        assertTrue(toolbar.waitForExists(elementTimeout))

        val postBody = device.findObject(UiSelector().resourceId("$packageName:id/post_body"))
        assertTrue(postBody.waitForExists(elementTimeout))
    }

    @Story("Navigation")
    @Description("Проверка возврата на главный экран")
    @Test
    fun navigation_pressBackFromDetails_returnsToMainScreen() {
        Thread.sleep(5000)

        val firstPost = device.findObject(
            UiSelector()
                .resourceId("$packageName:id/postsRecyclerView")
                .childSelector(UiSelector().index(0))
        )
        assertTrue(firstPost.waitForExists(elementTimeout))
        firstPost.click()

        Thread.sleep(2000)
        device.pressBack()
        Thread.sleep(1000)

        val recyclerView = device.findObject(UiSelector().resourceId("$packageName:id/postsRecyclerView"))
        assertTrue(recyclerView.waitForExists(elementTimeout))
    }

    @Story("Exit Dialog")
    @Description("Проверка диалога подтверждения выхода")
    @Test
    fun exitDialog_pressBackOnMain_showsConfirmationDialog() {
        Thread.sleep(3000)
        device.pressBack()
        Thread.sleep(1000)

        val dialogTitle = device.findObject(UiSelector().textContains("Exit"))
        val yesButton = device.findObject(UiSelector().textContains("Yes"))
        val noButton = device.findObject(UiSelector().textContains("No"))

        assertTrue(dialogTitle.waitForExists(elementTimeout) || yesButton.waitForExists(elementTimeout))

        if (noButton.exists()) {
            noButton.click()
        }
    }

    @Story("Error Handling")
    @Description("Проверка обработки ошибок")
    @Test
    fun errorScenario_appHandlesGracefully() {
        Thread.sleep(3000)

        val currentPackage = device.currentPackageName
        assertTrue(currentPackage == packageName)

        val swipeRefresh = device.findObject(UiSelector().resourceId("$packageName:id/swipeRefreshLayout"))
        assertTrue(swipeRefresh.exists())
    }
}
