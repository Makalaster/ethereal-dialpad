package com.makalaster.etherealdialpad.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.makalaster.etherealdialpad.navigation.EtherealDialpadNavHost
import com.makalaster.etherealdialpad.pads.PadActivity
import com.makalaster.etherealdialpad.prefs.SettingsActivity
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme

class HomeActivity : AppCompatActivity() {
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EtherealDialpadApp {
                goToSettings()
            }
        }

        viewModel.pads.observe(this) {
            launchPad(it)
        }
    }

    private fun goToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java).putExtra(SettingsActivity.PREFERENCE_PANE, SettingsActivity.SYNTH_PREFERENCES))
    }

    private fun launchPad(@StringRes name: Int) {
        if (name != -1) {
            startActivity(Intent(this, PadActivity::class.java).putExtra(PadActivity.PAD_NAME, name))
        }
    }
}

@Composable
fun EtherealDialpadApp(onSettingsClick: () -> Unit) {
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
