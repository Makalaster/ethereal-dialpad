package com.makalaster.etherealdialpad.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.main.adapter.PadsAdapter
import com.makalaster.etherealdialpad.pads.PadActivity
import com.makalaster.etherealdialpad.prefs.SettingsActivity
import com.makalaster.etherealdialpad.prefs.SynthPreferencesFragment

class MainActivity : AppCompatActivity(), PadsAdapter.OnPadClickListener {

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
                startActivity(Intent(this, SettingsActivity::class.java).putExtra(SettingsActivity.PREFERENCE_PANE, SettingsActivity.SYNTH_PREFERENCES))
            }
        }

        return false
    }

    private fun launchPad(@StringRes name: Int) {
        startActivity(Intent(this, PadActivity::class.java).putExtra(PadActivity.PAD_NAME, name))
    }

    override fun onPadClicked(@StringRes name: Int) {
        launchPad(name)
    }
}
