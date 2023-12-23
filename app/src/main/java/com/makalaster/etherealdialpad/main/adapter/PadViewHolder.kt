package com.makalaster.etherealdialpad.main.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.pads.Pad

class PadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(pad: Pad) {
        itemView.findViewById<ImageView>(R.id.pad_icon).setBackgroundResource(pad.icon)
        itemView.findViewById<TextView>(R.id.pad_name).setText(pad.name)
    }
}