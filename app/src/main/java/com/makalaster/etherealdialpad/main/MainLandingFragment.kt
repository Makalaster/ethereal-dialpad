package com.makalaster.etherealdialpad.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.main.adapter.PadsAdapter

class MainLandingFragment : Fragment() {

    companion object {
        const val TAG: String = "pad_list"
    }

    private lateinit var padsAdapter: PadsAdapter

    private lateinit var viewModel: MainLandingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_landing_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is PadsAdapter.OnPadClickListener) {
            padsAdapter = PadsAdapter(context)
        }
    }

    private fun initView() {
        view?.findViewById<RecyclerView>(R.id.pad_recycler)?.adapter = padsAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainLandingViewModel::class.java]

        viewModel.generatePads()

        viewModel.pads.observe(viewLifecycleOwner) {
            padsAdapter.setPads(it)
        }
    }
}