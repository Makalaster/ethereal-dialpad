package com.makalaster.ethereal_dialpad.pads.swarm

import androidx.lifecycle.viewModelScope
import com.makalaster.ethereal_dialpad.Constants.Preferences
import com.makalaster.ethereal_dialpad.dsp.Synth
import com.makalaster.ethereal_dialpad.prefs.PadSettings
import com.makalaster.ethereal_dialpad.pads.PadViewModel
import com.makalaster.ethereal_dialpad.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SwarmViewModel @Inject constructor(repo: PrefsRepository, synth: Synth) : PadViewModel(repo, synth) {
    override val pad: String = Preferences.SWARM_PREFS

    override val flow = prefsRepo.prefsFlow(Preferences.SWARM_PREFS).map {
        synth.refreshSettings(it)
        it
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PadSettings()
    )
}