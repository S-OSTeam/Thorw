package com.example.throw_fornt.feature.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.FragmentMapBinding
import com.example.throw_fornt.util.common.BindingFragment
import com.example.throw_fornt.util.common.Toaster
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val locationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                updateCurrentPosition(37.5, 128.5)
            } else if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                Toaster.showShort(requireContext(), "정확한 앱 동작을 위해 설정에서 정확한 위치정보로 수정해주세요.") // todo: 스낵바
                updateCurrentPosition(37.5, 128.5)
            } else {
                Toaster.showShort(requireContext(), "1위치 권한을 얻지 못했습니다") // todo: 스낵바
            }
        }

    private val viewModel: MapViewModel by viewModels()

    private var curPositionMarker: MapPOIItem? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
    }

    private fun updateCurrentPosition(latitude: Double, longitude: Double) {
        val mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        binding.mapView.setMapCenterPoint(mapPoint, true)
        binding.mapView.setZoomLevel(1, true)

        if (curPositionMarker == null) {
            curPositionMarker = MapPOIItem()
            curPositionMarker?.itemName = "현재 위치"
            curPositionMarker?.markerType =
                MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
            curPositionMarker?.selectedMarkerType =
                MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
            curPositionMarker?.mapPoint = mapPoint
            binding.mapView.addPOIItem(curPositionMarker)
        } else {
            curPositionMarker?.mapPoint = mapPoint
        }
    }

    override fun onStart() {
        super.onStart()
        checkLocationPermission()
    }

    private fun checkLocationPermission() {
        if (checkLocationService().not()) {
            Toaster.showShort(requireContext(), "GPS를 먼저 켜주세요.") // 스낵바 띄워줘야 할듯
        }

        // 둘 중에 한 개도 부여된 것이 없다면,,
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toaster.showShort(requireContext(), "원활한 동작을 위해 위치 권한을 켜주세요.")
            } else {
                locationResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                    ),
                )
            }
        } else {
            if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                Toaster.showShort(
                    requireContext(),
                    "대략적인 위치로 정보를 얻겠습니다.\n정확한 위치 정보로 수정하길 추천드립니다.",
                )
                // 이부분도 스낵바로? 교체해야 할듯.
            }
            updateCurrentPosition(37.5, 128.5)
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        curPositionMarker = null
    }
}
