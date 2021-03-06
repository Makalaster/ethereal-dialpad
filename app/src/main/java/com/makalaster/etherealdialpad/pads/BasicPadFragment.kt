package com.makalaster.etherealdialpad.pads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makalaster.etherealdialpad.R
import kotlinx.android.synthetic.main.fragment_basic_pad.*

class BasicPadFragment : BasePadFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basic_pad, container, false)
    }

    override fun onSynthServiceConnected() {
        flat_view.setSynthService(synthService)
    }
}