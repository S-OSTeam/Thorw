package com.example.throw_fornt.feature.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.FragmentMapBinding
import com.example.throw_fornt.util.common.BindingFragment

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val viewModel: MapViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }
}
