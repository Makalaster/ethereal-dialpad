package com.makalaster.etherealdialpad.prefs

import android.content.Context
import androidx.preference.PreferenceManager

object PrefsRepository {
    fun getQuantizer(ctx: Context): String? {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString("quantizer", "1,4,6,9,11")
    }

    fun getOctaves(ctx: Context): Int {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getString("octaves", "3")?.toInt() ?: 3
    }

    fun isDelayEnabled(ctx: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("delay", true)
    }

    fun isFlangeEnabled(ctx: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("flange", false)
    }

    fun isSoftTimbreEnabled(ctx: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("softTimbre", true)
    }

    fun isSoftEnvelopeEnabled(ctx: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("softEnvelope", true)
    }

    fun isDuetEnabled(ctx: Context): Boolean {
        return PreferenceManager.getDefaultSharedPreferences(ctx).getBoolean("duet", true)
    }
}