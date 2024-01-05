package com.makalaster.etherealdialpad.pads

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class PadSettings(
    var quantizer: String = "",
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
}
