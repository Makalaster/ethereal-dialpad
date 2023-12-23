package com.makalaster.etherealdialpad.pads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.pads.views.GridView

class GridPadFragment : BasePadFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_grid_pad, container, false)
    }

    override fun onSynthServiceConnected() {
        view?.findViewById<GridView>(R.id.grid_view)?.let {
            it.setSynthService(synthService)
            it.onConnected()
        }
    }
}