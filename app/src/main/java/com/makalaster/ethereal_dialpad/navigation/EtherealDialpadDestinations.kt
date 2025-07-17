package com.makalaster.ethereal_dialpad.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.makalaster.ethereal_dialpad.R
import kotlinx.serialization.Serializable

interface EtherealDialpadDestination {
    val iconId: Int
    val nameId: Int
    val descId: Int
}

@Serializable
object Home: EtherealDialpadDestination {
    @DrawableRes override val iconId = 0
    @StringRes override val nameId = 0
    @StringRes override val descId = 0
}

@Serializable
object FlatPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.basicpad_icon
    @StringRes override val nameId = R.string.flat_pad
    @StringRes override val descId = R.string.basicpad_label
}

@Serializable
object DrawPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.trailpad_icon
    @StringRes override val nameId = R.string.draw_pad
    @StringRes override val descId = R.string.trailpad_label
}

@Serializable
object SwarmPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.pointpad_icon
    @StringRes override val nameId = R.string.swarm_pad
    @StringRes override val descId = R.string.pointpad_label
}

@Serializable
object GridPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.gridpad_icon
    @StringRes override val nameId = R.string.grid_pad
    @StringRes override val descId = R.string.gridpad_label
}

@Serializable
object About