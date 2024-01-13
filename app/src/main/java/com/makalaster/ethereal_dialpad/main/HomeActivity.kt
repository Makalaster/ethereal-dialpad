package com.makalaster.ethereal_dialpad.main

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.makalaster.ethereal_dialpad.navigation.EtherealDialpadNavHost
import com.makalaster.ethereal_dialpad.ui.theme.EtherealDialpadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.getInsetsController(window, window.decorView).systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            EtherealDialpadApp(
                ::toggleSystemBars
            )
        }
    }

    private fun toggleSystemBars(show: Boolean) {
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        window.decorView.setOnApplyWindowInsetsListener { view, insets ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (show) {
                    windowInsetsController.show(WindowInsetsCompat.Type.systemBars())
                } else {
                    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
                }
            }

            view.onApplyWindowInsets(insets)
        }

        window.decorView.requestApplyInsets()
    }
}

@Composable
fun EtherealDialpadApp(
    toggleSystemBars: (show: Boolean) -> Unit
) {
    val navController = rememberNavController()
    EtherealDialpadTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            EtherealDialpadNavHost(
                navController = navController,
                toggleSystemBars
            )
        }
    }
}
