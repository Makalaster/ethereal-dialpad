package com.makalaster.etherealdialpad.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.makalaster.etherealdialpad.pads.Pad
import kotlinx.android.synthetic.main.layout_pad_viewholder.view.*

class PadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun onBindViewholder(pad: Pad) {
        itemView.pad_icon.setBackgroundResource(pad.icon)
        itemView.pad_name.setText(pad.name)
    }
}