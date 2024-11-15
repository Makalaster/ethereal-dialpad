package com.makalaster.ethereal_dialpad.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.makalaster.ethereal_dialpad.R

interface EtherealDialpadDestination {
    val iconId: Int
    val nameId: Int
    val descId: Int
    val route: String
}

object Home: EtherealDialpadDestination {
    @DrawableRes override val iconId = 0
    @StringRes override val nameId = 0
    @StringRes override val descId = 0
    override val route = "home"
}

object FlatPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.basicpad_icon
    @StringRes override val nameId = R.string.flat_pad
    @StringRes override val descId = R.string.basicpad_label
    override val route = "basic_pad"
}

object DrawPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.trailpad_icon
    @StringRes override val nameId = R.string.draw_pad
    @StringRes override val descId = R.string.trailpad_label
    override val route = "draw_pad"
}

object SwarmPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.pointpad_icon
    @StringRes override val nameId = R.string.swarm_pad
    @StringRes override val descId = R.string.pointpad_label
    override val route = "swarm_pad"
}

object GridPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.gridpad_icon
    @StringRes override val nameId = R.string.grid_pad
    @StringRes override val descId = R.string.gridpad_label
    override val route = "grid_pad"
}

val pads = listOf(FlatPad, DrawPad, SwarmPad)