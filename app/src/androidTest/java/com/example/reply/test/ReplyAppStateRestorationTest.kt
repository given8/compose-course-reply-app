package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
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
    @TestCompactWidth
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

    @Test
    @TestExpandedWidth
    fun expandedScreen_selectedEmailRetained_afterConfigChange(){
        val stateRestorationTester = StateRestorationTester(composeTestRule)

        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Medium)
        }

        composeTestRule.onNodeWithStringId(LocalEmailsDataProvider.allEmails[2].body).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(LocalEmailsDataProvider.allEmails[2].subject).performClick()

        composeTestRule.onNodeWithTagForStringId(com.example.reply.R.string.details_screen)
            .onChildren()
            .assertAny(hasAnyDescendant(hasText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))))

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithTagForStringId(com.example.reply.R.string.details_screen)
            .onChildren()
            .assertAny(hasAnyDescendant(hasText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))))

    }
}