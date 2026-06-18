package com.blank.fakeapp.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.blank.fakeapp.MainActivity
import com.blank.fakeapp.R
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun test_bottom_navigation_is_displayed() {
        // Wait until the bottom nav is displayed to ensure app is loaded
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithTag("bottom_nav").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("bottom_nav").assertIsDisplayed()
        composeTestRule.onNodeWithTag("nav_item_products").assertIsDisplayed()
        composeTestRule.onNodeWithTag("nav_item_favorites").assertIsDisplayed()
        composeTestRule.onNodeWithTag("nav_item_profile").assertIsDisplayed()
    }

    @Test
    fun test_navigation_to_favorites_screen() {
        val noFavoritesText = composeTestRule.activity.getString(R.string.no_favorites_found)

        // Wait for bottom nav to appear
        composeTestRule.onNodeWithTag("nav_item_favorites").assertExists().performClick()

        // Wait for the text to appear in the favorites screen
        composeTestRule.waitUntil(timeoutMillis = 10000) {
            composeTestRule.onAllNodesWithText(noFavoritesText).fetchSemanticsNodes().isNotEmpty()
        }
        
        composeTestRule.onNodeWithText(noFavoritesText).assertIsDisplayed()
    }

    @Test
    fun test_navigation_to_profile_screen() {
        val profileTitle = composeTestRule.activity.getString(R.string.screen_profile)

        composeTestRule.onNodeWithTag("nav_item_profile").assertExists().performClick()

        // Check if the title appears in the TopBar
        composeTestRule.onNode(
            hasParent(hasTestTag("main_top_bar")).and(hasText(profileTitle))
        ).assertIsDisplayed()
    }
}
