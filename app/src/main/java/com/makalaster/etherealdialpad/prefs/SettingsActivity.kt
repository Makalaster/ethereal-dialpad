package com.makalaster.etherealdialpad.prefs

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import com.makalaster.etherealdialpad.R

class SettingsActivity : AppCompatActivity() {
    companion object {
        const val PREFERENCE_PANE = "preferences pane"
        const val SYNTH_PREFERENCES = "synth preferences"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = when (intent.getStringExtra(PREFERENCE_PANE)) {
            SYNTH_PREFERENCES -> SynthPreferencesFragment()
            else -> SynthPreferencesFragment()
        }

        showSettingsPane(fragment, fragment.tag.toString())
    }

    private fun showSettingsPane(preferencesFragment: PreferenceFragmentCompat, tag: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.settings, preferencesFragment, tag)
            .addToBackStack(SynthPreferencesFragment.TAG).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }
}