package com.makalaster.etherealdialpad.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.synthprefs.SynthSettingsFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        supportFragmentManager.beginTransaction().add(R.id.fragment_container, MainLandingFragment(), MainLandingFragment.TAG).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.synth_preferences_menu -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SynthSettingsFragment(), SynthSettingsFragment.TAG).addToBackStack(SynthSettingsFragment.TAG).commit()
            }
        }

        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()

        supportFragmentManager.popBackStack()
    }

    private fun launchPad() {

    }
}
