package com.makalaster.ethereal_dialpad.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.makalaster.ethereal_dialpad.navigation.EtherealDialpadNavHost
import com.makalaster.ethereal_dialpad.ui.theme.EtherealDialpadTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EtherealDialpadApp()
        }
    }
}

@Composable
fun EtherealDialpadApp() {
    val navController = rememberNavController()
    EtherealDialpadTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            EtherealDialpadNavHost(
                navController = navController
            )
        }
    }
}
