package com.makalaster.etherealdialpad.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.makalaster.etherealdialpad.navigation.EtherealDialpadNavHost
import com.makalaster.etherealdialpad.prefs.SettingsActivity
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EtherealDialpadApp(
                ::goToSettings
            )
        }
    }

    private fun goToSettings() {
        startActivity(
            Intent(this, SettingsActivity::class.java)
                .putExtra(SettingsActivity.PREFERENCE_PANE, SettingsActivity.SYNTH_PREFERENCES)
        )
    }
}

@Composable
fun EtherealDialpadApp(
    onSettingsClick: () -> Unit
) {
    val navController = rememberNavController()
    EtherealDialpadTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            EtherealDialpadNavHost(
                navController = navController,
                onSettingsClick
            )
        }
    }
}
