package com.makalaster.etherealdialpad.synthprefs

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.makalaster.etherealdialpad.R

class SynthSettingsFragment : PreferenceFragmentCompat() {
    companion object {
        const val TAG: String = "synth settings"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.synth_prefs, TAG)
    }
}