package com.makalaster.etherealdialpad.synthprefs

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.makalaster.etherealdialpad.R

class SynthPreferencesFragment : PreferenceFragmentCompat() {
    companion object {
        val TAG: String = SynthPreferencesFragment::class.java.name
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.synth_prefs, rootKey)
    }
}