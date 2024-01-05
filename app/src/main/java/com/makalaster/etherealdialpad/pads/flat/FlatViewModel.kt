package com.makalaster.etherealdialpad.pads.flat

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import com.makalaster.etherealdialpad.Constants.Preferences
import com.makalaster.etherealdialpad.dsp.Synth
import com.makalaster.etherealdialpad.pads.PadSettings
import com.makalaster.etherealdialpad.pads.PadViewModel
import com.makalaster.etherealdialpad.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class FlatViewModel @Inject constructor(repo: PrefsRepository, synth: Synth) : PadViewModel(repo, synth) {
    val flatPrefsFlow = prefsRepo.prefsFlow(Preferences.FLAT_PREFS).map {
        synth.refreshSettings(it)
        it
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        PadSettings()
    )

    fun updateFlatPref(pref: String, value: Boolean) {
        viewModelScope.launch {
            prefsRepo.setBooleanPref(Preferences.FLAT_PREFS, pref, value)
        }
    }

    fun transform(point: Float, dimen: Float): Float = min(1f, max(0f, point / dimen))

    fun colorTransform(x: Float, width: Float, y: Float, height: Float): Color {
        val u = transform(x, width)
        val v = transform(y, height)

        val r = (128 + 128 * (u - 0.5f)).toInt()
        val g = (128 + 128 * (v - 0.5f)).toInt()
        val b = (128 + 128 * (0.5f - u)).toInt()

        return Color(r, g, b)
    }
}