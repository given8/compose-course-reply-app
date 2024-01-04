package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun compactScreen_selectedEmailEmailRetained_afterConfigChange(){
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertIsDisplayed()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)).performClick()

        composeTestRule.onNodeWithContentDescriptionForStringId(com.example.reply.R.string.navigation_back).assertExists()

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)).assertExists()

        composeTestRule.onNodeWithContentDescriptionForStringId(com.example.reply.R.string.navigation_back).assertExists()
    }
}