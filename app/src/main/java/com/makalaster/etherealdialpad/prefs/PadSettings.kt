package com.makalaster.etherealdialpad.prefs

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PadSettings(
    var quantizer: PitchQuantizer = PitchQuantizer.PENTATONIC2,
    var octaves: String = "",
    delay: Boolean = true,
    flange: Boolean = false,
    softEnvelope: Boolean = true,
    softTimbre: Boolean = true,
    duet: Boolean = true
) {
    var delay by mutableStateOf(delay)
    var flange by mutableStateOf(flange)
    var softEnvelope by mutableStateOf(softEnvelope)
    var softTimbre by mutableStateOf(softTimbre)
    var duet by mutableStateOf(duet)

    enum class PitchQuantizer(val quantizerName: String, val value: String) {
        CHROMATIC("Chromatic", "0,1,2,3,4,5,6,7,8,9,10,11"),
        MAJOR("Major", "0,2,4,5,7,9,11"),
        MINOR("Minor", "0,2,4,5,7,8,11"),
        PENTATONIC("Pentatonic", "0,2,4,7,9"),
        PENTATONIC2("Pentatonic2", "1,4,6,9,11"),
        OCTAVE("Octave", "0"),
        THEREMIN("Theremin", "");
    }
}

fun String.toQuantizer(): PadSettings.PitchQuantizer = PadSettings.PitchQuantizer.valueOf(this.uppercase())