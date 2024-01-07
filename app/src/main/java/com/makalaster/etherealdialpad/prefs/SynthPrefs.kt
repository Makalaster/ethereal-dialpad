package com.makalaster.etherealdialpad.prefs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.makalaster.etherealdialpad.Constants.Preferences
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.ui.theme.EtherealDialpadTheme

@Composable
fun PreferencesBottomSheet(
    prefState: PadSettings,
    onCheckedChange: (String, Boolean) -> Unit,
    onSelectionChanged: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ExpandableRadioGroup(
            text = stringResource(id = R.string.pref_quantizer),
            options = PadSettings.PitchQuantizer.entries.map { it.quantizerName },
            selected = prefState.quantizer.quantizerName,
            onSelectionChanged = { onSelectionChanged(Preferences.QUANTIZER, it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExpandableRadioGroup(
            text = stringResource(id = R.string.pref_octaves),
            options = listOf("1", "2", "3", "4"),
            selected = prefState.octaves,
            onSelectionChanged = { onSelectionChanged(Preferences.OCTAVES, it) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxRow(
            prefState.delay,
            onCheckedChange = onCheckedChange,
            text = stringResource(id = R.string.pref_delay),
            prefKey = Preferences.DELAY
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxRow(
            prefState.flange,
            onCheckedChange = onCheckedChange,
            text = stringResource(id = R.string.pref_flange),
            prefKey = Preferences.FLANGE
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxRow(
            prefState.softEnvelope,
            onCheckedChange = onCheckedChange,
            text = stringResource(id = R.string.pref_soft_envelope),
            prefKey = Preferences.SOFT_ENVELOPE
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxRow(
            prefState.softTimbre,
            onCheckedChange = onCheckedChange,
            text = stringResource(id = R.string.pref_soft_timbre),
            prefKey = Preferences.SOFT_TIMBRE
        )
        Spacer(modifier = Modifier.height(8.dp))
        CheckboxRow(
            prefState.duet,
            onCheckedChange = onCheckedChange,
            text = stringResource(id = R.string.pref_duet),
            prefKey = Preferences.DUET
        )
    }
}

@Composable
fun CheckboxRow(
    isChecked: Boolean,
    onCheckedChange: (String, Boolean) -> Unit,
    text: String,
    prefKey: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .toggleable(
                value = isChecked,
                onValueChange = { onCheckedChange(prefKey, it) },
                role = Role.Checkbox
            )
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1F)
        )
        Checkbox(
            checked = isChecked,
            onCheckedChange = null
        )
    }
}

@Composable
fun ExpandableRadioGroup(
    text: String,
    options: List<String>,
    selected: String,
    onSelectionChanged: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier.height(48.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        expanded = !expanded
                    }
            )
        }

        AnimatedVisibility(visible = expanded) {
            Column(Modifier.selectableGroup()) {
                options.forEach {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .selectable(
                                selected = (it == selected),
                                onClick = { onSelectionChanged(it) },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (it == selected),
                            onClick = null
                        )
                        Spacer(modifier = Modifier.padding(start = 16.dp))
                        Text(
                            text = it,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreferencesPreview() {
    EtherealDialpadTheme {
        PreferencesBottomSheet(
            PadSettings(),
            { _, _ -> },
            { _, _ -> }
        )
    }
}