package com.makalaster.etherealdialpad.pads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.pads.views.FlatView

class BasicPadFragment : BasePadFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_pad, container, false)
    }

    override fun onSynthServiceConnected() {
        view?.findViewById<FlatView>(R.id.flat_view)?.setSynthService(synthService)
    }
}