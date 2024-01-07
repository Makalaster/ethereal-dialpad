package com.makalaster.etherealdialpad.prefs

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.makalaster.etherealdialpad.pads.PadSettings
import com.makalaster.etherealdialpad.pads.toQuantizer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import com.makalaster.etherealdialpad.Constants.Preferences as SynthPrefs

class PrefsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    fun prefsFlow(pad: String): Flow<PadSettings> = dataStore.data
        .catch {
            if (it is IOException) {
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { prefs ->
            PadSettings(
                quantizer = (prefs[stringPreferencesKey(pad + SynthPrefs.QUANTIZER)] ?: PadSettings.PitchQuantizer.PENTATONIC2.quantizerName).toQuantizer(),
                octaves = prefs[stringPreferencesKey(pad + SynthPrefs.OCTAVES)] ?: "3",
                delay = prefs[booleanPreferencesKey(pad + SynthPrefs.DELAY)] ?: true,
                flange = prefs[booleanPreferencesKey(pad + SynthPrefs.FLANGE)] ?: false,
                softEnvelope = prefs[booleanPreferencesKey(pad + SynthPrefs.SOFT_ENVELOPE)] ?: true,
                softTimbre = prefs[booleanPreferencesKey(pad + SynthPrefs.SOFT_TIMBRE)] ?: true,
                duet = prefs[booleanPreferencesKey(pad + SynthPrefs.DUET)] ?: true
            )
        }

    suspend fun setStringPref(pad: String, pref: String, selection: String) {
        dataStore.edit {
            it[stringPreferencesKey(pad + pref)] = selection
        }
    }

    suspend fun setBooleanPref(pad: String, pref: String, value: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(pad + pref)] = value
        }
    }
}