package dev.shreyaspatil.foodium.utils

import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.Calendar

@Epic("Unit Tests")
@Feature("TimeUtils")
@RunWith(JUnit4::class)
class TimeUtilsTest {

    @Before
    fun setup() {
        mockkStatic(Calendar::class)
    }

    @After
    fun tearDown() {
        unmockkStatic(Calendar::class)
    }

    @Story("isNight")
    @Description("Проверка раннего утра (час <= 7)")
    @Test
    fun isNight_earlyMorning_returnsTrue() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 5)
        every { Calendar.getInstance() } returns mockCalendar

        assertTrue(isNight())
    }

    @Story("isNight")
    @Description("Проверка позднего вечера (час >= 18)")
    @Test
    fun isNight_lateNight_returnsTrue() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 21)
        every { Calendar.getInstance() } returns mockCalendar

        assertTrue(isNight())
    }

    @Story("isNight")
    @Description("Проверка дневного времени (8 <= час < 18)")
    @Test
    fun isNight_dayTime_returnsFalse() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 12)
        every { Calendar.getInstance() } returns mockCalendar

        assertFalse(isNight())
    }

    @Story("isNight Boundary")
    @Description("Граничное значение: час = 7")
    @Test
    fun isNight_boundaryAt7_returnsTrue() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 7)
        every { Calendar.getInstance() } returns mockCalendar

        assertTrue(isNight())
    }

    @Story("isNight Boundary")
    @Description("Граничное значение: час = 18")
    @Test
    fun isNight_boundaryAt18_returnsTrue() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 18)
        every { Calendar.getInstance() } returns mockCalendar

        assertTrue(isNight())
    }

    @Story("isNight Boundary")
    @Description("Граничное значение: час = 8")
    @Test
    fun isNight_boundaryAt8_returnsFalse() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 8)
        every { Calendar.getInstance() } returns mockCalendar

        assertFalse(isNight())
    }

    @Story("isNight Boundary")
    @Description("Граничное значение: час = 17")
    @Test
    fun isNight_boundaryAt17_returnsFalse() {
        val mockCalendar = Calendar.getInstance()
        mockCalendar.set(Calendar.HOUR_OF_DAY, 17)
        every { Calendar.getInstance() } returns mockCalendar

        assertFalse(isNight())
    }
}
