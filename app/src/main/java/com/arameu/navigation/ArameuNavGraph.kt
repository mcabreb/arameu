package com.arameu.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

object Routes {
    const val WELCOME = "welcome"
    const val COURSE = "course"
    const val LESSON = "lesson/{lessonId}"

    fun lesson(lessonId: Int) = "lesson/$lessonId"
}

@Composable
fun ArameuNavGraph(
    navController: NavHostController,
    startDestination: String,
    welcomeScreen: @Composable () -> Unit,
    courseScreen: @Composable (onLessonClick: (Int) -> Unit) -> Unit,
    lessonScreen: @Composable (lessonId: Int, onFinished: () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = androidx.compose.animation.core.tween(300)) },
        exitTransition = { fadeOut(animationSpec = androidx.compose.animation.core.tween(300)) },
    ) {
        composable(Routes.WELCOME) {
            welcomeScreen()
        }

        composable(Routes.COURSE) {
            courseScreen { lessonId ->
                navController.navigate(Routes.lesson(lessonId)) {
                    popUpTo(Routes.COURSE) { inclusive = false }
                }
            }
        }

        composable(
            route = Routes.LESSON,
            arguments = listOf(navArgument("lessonId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getInt("lessonId") ?: return@composable
            lessonScreen(lessonId) {
                navController.popBackStack()
            }
        }
    }
}
