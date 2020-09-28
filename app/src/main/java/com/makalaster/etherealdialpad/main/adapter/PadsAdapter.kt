package com.makalaster.etherealdialpad.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.makalaster.etherealdialpad.pads.Pad
import com.makalaster.etherealdialpad.R
import kotlinx.android.synthetic.main.layout_pad_viewholder.view.*

class PadsAdapter(listener: OnPadClickListener): RecyclerView.Adapter<PadViewHolder>() {
    private val padList: MutableList<Pad> = ArrayList()
    private val padClickListener: OnPadClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PadViewHolder {
        return PadViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_pad_viewholder, parent, false))
    }

    override fun onBindViewHolder(holder: PadViewHolder, position: Int) {
        holder.bind(padList[position])
        holder.itemView.root_view.setOnClickListener {
            padClickListener.onPadClicked(padList[position].name)
        }
    }

    override fun getItemCount(): Int {
        return padList.size
    }

    fun setPads(pads: List<Pad>) {
        padList.clear()
        padList.addAll(pads)
    }

    interface OnPadClickListener{
        fun onPadClicked(@StringRes name: Int)
    }
}