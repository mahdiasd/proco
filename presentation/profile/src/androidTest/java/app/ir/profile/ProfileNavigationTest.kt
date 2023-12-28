package app.ir.profile

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import app.ir.profile.navigation.navigateToProfile
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ProfileNavigationTest {

    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupMoodTrackerAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
        }
    }

    @Test
    fun moodTrackerNavHost_verifyStartDestination() {
        navController.navigateToProfile(1)
        val arguments = navController.currentDestination?.arguments
        assertTrue(arguments?.containsKey("id") == false)

    }

}