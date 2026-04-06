package com.arameu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.lifecycleScope
import com.arameu.audio.AudioManager
import com.arameu.data.ArameuDatabase
import com.arameu.data.ContentLoader
import com.arameu.data.repository.CourseRepository
import com.arameu.data.repository.ProgressRepository
import kotlinx.coroutines.launch
import com.arameu.navigation.ArameuNavGraph
import com.arameu.navigation.Routes
import com.arameu.ui.course.CourseMapScreen
import com.arameu.ui.course.CourseMapViewModel
import com.arameu.ui.lesson.LessonScreen
import com.arameu.ui.lesson.LessonViewModel
import com.arameu.ui.theme.ArameuTheme
import com.arameu.ui.welcome.WelcomeScreen

class MainActivity : ComponentActivity() {

    private lateinit var audioManager: AudioManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioManager = AudioManager(applicationContext)

        val prefs = getSharedPreferences("arameu_prefs", MODE_PRIVATE)
        val isFirstLaunch = !prefs.getBoolean("first_launch_done", false)
        val startDestination = if (isFirstLaunch) Routes.WELCOME else Routes.COURSE

        val db = ArameuDatabase.getInstance(this)
        val courseRepository = CourseRepository(db.courseDao())
        val progressRepository = ProgressRepository(db.progressDao())

        // Load content from JSON into Room on first launch
        val contentLoader = ContentLoader(this, db.courseDao(), prefs)
        lifecycleScope.launch { contentLoader.loadIfNeeded() }

        setContent {
            ArameuTheme {
                val navController = rememberNavController()
                val courseMapViewModel = remember {
                    CourseMapViewModel(courseRepository, progressRepository)
                }

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
                            CourseMapScreen(
                                viewModel = courseMapViewModel,
                                onLessonClick = onLessonClick,
                                modifier = Modifier.padding(innerPadding),
                            )
                        },
                        lessonScreen = { lessonId, onFinished ->
                            val lessonViewModel = remember(lessonId) {
                                LessonViewModel(
                                    lessonId = lessonId,
                                    courseRepository = courseRepository,
                                    progressRepository = progressRepository,
                                )
                            }
                            LessonScreen(
                                viewModel = lessonViewModel,
                                onFinished = onFinished,
                                onBack = onFinished,
                                onPlayAudio = { audioId ->
                                    // Resolve audioId to asset path and play
                                    // Audio IDs are like "a_aleph" → "audio/unit1/a-aleph.mp3"
                                    // We don't know the unit from audioId alone, so try all units
                                    val path = audioId.lowercase()
                                        .replace("_", "-")
                                        .replace(" ", "-")
                                    // Try unit directories in order
                                    for (unit in 1..3) {
                                        val assetPath = "audio/unit$unit/$path.mp3"
                                        try {
                                            assets.open(assetPath).close()
                                            audioManager.play(assetPath)
                                            return@LessonScreen
                                        } catch (_: Exception) {
                                            // Try next unit
                                        }
                                    }
                                },
                                modifier = Modifier.padding(innerPadding),
                            )
                        },
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::audioManager.isInitialized) {
            audioManager.release()
        }
    }
}
