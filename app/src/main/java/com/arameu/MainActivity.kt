package com.arameu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.arameu.data.ArameuDatabase
import com.arameu.data.repository.CourseRepository
import com.arameu.data.repository.ProgressRepository
import com.arameu.navigation.ArameuNavGraph
import com.arameu.navigation.Routes
import com.arameu.ui.course.CourseMapScreen
import com.arameu.ui.course.CourseMapViewModel
import com.arameu.ui.lesson.LessonScreen
import com.arameu.ui.lesson.LessonViewModel
import com.arameu.ui.theme.ArameuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = getSharedPreferences("arameu_prefs", MODE_PRIVATE)
        val isFirstLaunch = !prefs.getBoolean("first_launch_done", false)
        val startDestination = if (isFirstLaunch) Routes.WELCOME else Routes.COURSE

        val db = ArameuDatabase.getInstance(this)
        val courseRepository = CourseRepository(db.courseDao())
        val progressRepository = ProgressRepository(db.progressDao())

        setContent {
            ArameuTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArameuNavGraph(
                        navController = navController,
                        startDestination = startDestination,
                        welcomeScreen = {
                            WelcomeScreen(
                                onContinue = {
                                    prefs.edit().putBoolean("first_launch_done", true).apply()
                                    navController.navigate(Routes.COURSE) {
                                        popUpTo(Routes.WELCOME) { inclusive = true }
                                    }
                                },
                                modifier = Modifier.padding(innerPadding),
                            )
                        },
                        courseScreen = { onLessonClick ->
                            val viewModel = CourseMapViewModel(courseRepository, progressRepository)
                            CourseMapScreen(
                                viewModel = viewModel,
                                onLessonClick = onLessonClick,
                                modifier = Modifier.padding(innerPadding),
                            )
                        },
                        lessonScreen = { lessonId, onFinished ->
                            val lessonViewModel = LessonViewModel(
                                lessonId = lessonId,
                                courseRepository = courseRepository,
                                progressRepository = progressRepository,
                            )
                            LessonScreen(
                                viewModel = lessonViewModel,
                                onFinished = onFinished,
                                modifier = Modifier.padding(innerPadding),
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // Decorative Aramaic letter
        Text(
            text = "ܐ",
            style = MaterialTheme.typography.displayLarge.copy(fontSize = 72.sp),
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.welcome_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Arameu bíblic",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Un viatge per aprendre arameu bíblic, pas a pas.",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        ) {
            Text(
                text = stringResource(R.string.btn_start),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Composable
fun PlaceholderLessonScreen(
    lessonId: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Lliçó $lessonId",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            text = "Pròximament...",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 12.dp),
        )
    }
}
