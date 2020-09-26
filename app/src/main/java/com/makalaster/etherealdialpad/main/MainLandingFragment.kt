package com.makalaster.etherealdialpad.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.main.adapter.PadsAdapter
import kotlinx.android.synthetic.main.main_landing_fragment.*

class MainLandingFragment : Fragment() {

    companion object {
        const val TAG: String = "pad_list"
    }

    private val padsAdapter = PadsAdapter()

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

    private fun initView() {
        with(pad_recycler) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = padsAdapter
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainLandingViewModel::class.java)

        viewModel.generatePads()

        viewModel.pads.observe(viewLifecycleOwner, {
            padsAdapter.setPads(it)
        })
    }
}