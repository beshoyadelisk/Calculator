package com.beshoy.thirdwayvcalculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.beshoy.thirdwayvcalculator.ui.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SelectOperationTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private fun clickAdd() {
        onView(withId(R.id.btnAdd))
            .perform(click())
    }

    private fun writeData(data: String) {
        onView(withId(R.id.etOperand))
            .perform(typeText(data), closeSoftKeyboard())
    }

    private fun clickEqual() {
        onView(withId(R.id.btnEqual))
            .perform(click())
    }

    @Test
    fun pressOperationTest() {
        clickAdd()
        onView(withId(R.id.etOperand))
            .check(matches(isEnabled()))
        onView(withId(R.id.btnEqual))
            .check(matches(isNotEnabled()))
    }

    @Test
    fun writeOperandCheck() {
        clickAdd()
        writeData("3")
        onView(withId(R.id.btnEqual))
            .check(matches(isEnabled()))
    }

    @Test
    fun doOperationTest() {
        clickAdd()
        writeData("4")
        clickEqual()
        onView(withId(R.id.etOperand))
            .check(matches(isNotEnabled()))
        onView(withId(R.id.btnAdd))
            .check(matches(isNotSelected()))
    }

    @Test
    fun undoBtnTest() {
        onView(withId(R.id.btnUndo))
            .check(matches(isNotEnabled()))
        clickAdd()
        writeData("4")
        clickEqual()
        clickAdd()
        writeData("3")
        clickEqual()
        onView(withId(R.id.btnUndo))
            .check(matches(isEnabled()))
        onView(withId(R.id.btnUndo))
            .perform(click())
        onView(withId(R.id.btnUndo))
            .check(matches(isEnabled()))
    }

    @Test
    fun redoBtnTest() {
        onView(withId(R.id.btnRedo))
            .check(matches(isNotEnabled()))
        clickAdd()
        writeData("4")
        clickEqual()
        clickAdd()
        writeData("3")
        clickEqual()
        onView(withId(R.id.btnUndo))
            .perform(click())
        onView(withId(R.id.btnRedo))
            .check(matches(isEnabled()))
        onView(withId(R.id.btnRedo))
            .perform(click())
        onView(withId(R.id.btnRedo))
            .check(matches(isNotEnabled()))
    }
}