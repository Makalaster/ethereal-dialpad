package com.makalaster.etherealdialpad.prefs

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.makalaster.etherealdialpad.Constants.Preferences
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.pads.PadSettings
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme

@Composable
fun PreferencesBottomSheet(
    prefState: PadSettings,
    onCheckedChange: (String, Boolean) -> Unit = { _, _ -> }
) {
    Column {
        Text(text = stringResource(id = R.string.pref_quantizer))
        Text(text = stringResource(id = R.string.pref_octaves))
        CheckboxRow(
            prefState.delay,
            onCheckedChange = onCheckedChange,
            textId = R.string.pref_delay,
            prefKey = Preferences.DELAY
        )
        CheckboxRow(
            prefState.flange,
            onCheckedChange = onCheckedChange,
            textId = R.string.pref_flange,
            prefKey = Preferences.FLANGE
        )
        CheckboxRow(
            prefState.softEnvelope,
            onCheckedChange = onCheckedChange,
            textId = R.string.pref_soft_envelope,
            prefKey = Preferences.SOFT_ENVELOPE
        )
        CheckboxRow(
            prefState.softTimbre,
            onCheckedChange = onCheckedChange,
            textId = R.string.pref_soft_timbre,
            prefKey = Preferences.SOFT_TIMBRE
        )
        CheckboxRow(
            prefState.duet,
            onCheckedChange = onCheckedChange,
            textId = R.string.pref_duet_on,
            prefKey = Preferences.DUET
        )
    }
}

@Composable
fun CheckboxRow(
    isChecked: Boolean,
    onCheckedChange: (String, Boolean) -> Unit,
    @StringRes textId: Int,
    prefKey: String
) {
    Row {
        Text(text = stringResource(id = textId))
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(prefKey, it)
            }
        )
    }
}

@Preview
@Composable
fun PreferencesPreview() {
    EtherealDialpadTheme {
        PreferencesBottomSheet(PadSettings())
    }
}