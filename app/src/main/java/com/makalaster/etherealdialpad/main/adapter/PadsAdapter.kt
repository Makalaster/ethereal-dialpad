package com.makalaster.etherealdialpad.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.makalaster.etherealdialpad.pads.Pad
import com.makalaster.etherealdialpad.R

class PadsAdapter: RecyclerView.Adapter<PadViewHolder>() {
    private val padList: MutableList<Pad> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PadViewHolder {
        return PadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_pad_viewholder, parent, false))
    }

    override fun onBindViewHolder(holder: PadViewHolder, position: Int) {
        holder.onBindViewholder(padList[position])
    }

    override fun getItemCount(): Int {
        return padList.size
    }

    fun setPads(pads: List<Pad>) {
        padList.clear()
        padList.addAll(pads)
    }
}