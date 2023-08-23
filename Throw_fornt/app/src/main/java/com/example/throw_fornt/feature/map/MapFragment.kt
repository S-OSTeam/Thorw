package com.example.throw_fornt.feature.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.FragmentMapBinding
import com.example.throw_fornt.util.common.BindingFragment
import net.daum.mf.map.api.MapView

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val viewModel: MapViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    override fun onStart() {
        super.onStart()
        startTracking()
        stopTracking()
    }

    // 위치추적 시작
    private fun startTracking() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOnWithHeading
    }

    // 위치추적 중지
    private fun stopTracking() {
        binding.mapView.currentLocationTrackingMode =
            MapView.CurrentLocationTrackingMode.TrackingModeOff
    }
}
