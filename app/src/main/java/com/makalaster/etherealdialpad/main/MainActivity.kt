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
import com.makalaster.etherealdialpad.synthprefs.SynthPreferencesFragment

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
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SynthPreferencesFragment(), SynthPreferencesFragment.TAG).addToBackStack(SynthPreferencesFragment.TAG).commit()
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }

        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()

        supportFragmentManager.popBackStack()
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun launchPad(@StringRes name: Int) {
        startActivity(Intent(this, PadActivity::class.java).putExtra(PadActivity.PAD_NAME, name))
    }

    override fun onPadClicked(@StringRes name: Int) {
        launchPad(name)
    }
}
