package com.makalaster.etherealdialpad.pads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.pads.views.TrailView

class TrailPadFragment : BasePadFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trail_pad, container, false)
    }

    override fun onSynthServiceConnected() {
        view?.findViewById<TrailView>(R.id.trail_view)?.setSynthService(synthService)
    }
}