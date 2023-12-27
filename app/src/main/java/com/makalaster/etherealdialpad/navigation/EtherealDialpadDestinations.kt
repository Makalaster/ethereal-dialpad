package com.makalaster.etherealdialpad.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.makalaster.etherealdialpad.R

interface EtherealDialpadDestination {
    val iconId: Int
    val nameId: Int
    val route: String
}

object Home: EtherealDialpadDestination {
    @DrawableRes override val iconId = 0
    @StringRes override val nameId = 0
    override val route = "home"
}

object BasicPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.basicpad_icon
    @StringRes override val nameId = R.string.basicpad_label
    override val route = "basic_pad"
}

object DrawPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.trailpad_icon
    @StringRes override val nameId = R.string.trailpad_label
    override val route = "draw_pad"
}

object SwarmPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.pointpad_icon
    @StringRes override val nameId = R.string.pointpad_label
    override val route = "swarm_pad"
}

object GridPad: EtherealDialpadDestination {
    @DrawableRes override val iconId = R.drawable.gridpad_icon
    @StringRes override val nameId = R.string.gridpad_label
    override val route = "grid_pad"
}

object Settings: EtherealDialpadDestination {
    @DrawableRes override val iconId = 0
    @StringRes override val nameId = 0
    override val route = "settings"
}

val pads = listOf(BasicPad, DrawPad, SwarmPad, GridPad)