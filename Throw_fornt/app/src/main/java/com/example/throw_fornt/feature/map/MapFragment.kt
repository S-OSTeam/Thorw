package com.example.throw_fornt.feature.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.FragmentMapBinding
import com.example.throw_fornt.models.GeoPoint
import com.example.throw_fornt.models.MapStoreInfo
import com.example.throw_fornt.util.common.BindingFragment
import com.example.throw_fornt.util.common.Toaster
import com.example.throw_fornt.util.common.showSnackbar
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class MapFragment : BindingFragment<FragmentMapBinding>(R.layout.fragment_map) {

    private val locationResultLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions(),
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) return@registerForActivityResult

            if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                showSnackBar(R.string.map_location_permission_upgrade_require_message) { navigateToPermissionSetting() }
            } else {
                showSnackBar(R.string.map_location_permission_require_message) { navigateToPermissionSetting() }
            }
        }

    private val viewModel: MapViewModel by viewModels()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var curPositionMarker: MapPOIItem? = null

    private var nearByStoreMarkers: Array<MapPOIItem> = arrayOf()

    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                Log.d(
                    "mendel",
                    "제공자: ${location.provider}, ${location.latitude}, ${location.longitude}",
                )
                Toaster.showShort(
                    requireContext(),
                    "제공자: ${location.provider}, ${location.latitude}, ${location.longitude}",
                )
                viewModel.updateCurPosition(GeoPoint(location.latitude, location.longitude))
                break
            }
        }
    }

    private val locationRequest = LocationRequest.create().apply {
        interval = 2000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        checkLocationPermission()
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.curCameraCenterPoint.observe(viewLifecycleOwner) {
            val mapPoint = MapPoint.mapPointWithGeoCoord(it.latitude, it.longitude)
            binding.mapView.setMapCenterPoint(mapPoint, true)
            setMinDistance()
        }
        viewModel.lastUserPoint.observe(viewLifecycleOwner) {
            val mapPoint = MapPoint.mapPointWithGeoCoord(it.latitude, it.longitude)
            if (curPositionMarker == null) {
                curPositionMarker = MapPOIItem().apply {
                    itemName = "현재 위치"
                    markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                    selectedMarkerType =
                        MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    this.mapPoint = mapPoint
                    markerType = MapPOIItem.MarkerType.CustomImage // 마커타입을 커스텀 마커로 지정.
                    customImageResourceId = R.drawable.ic_map_pin_user_24
                    isCustomImageAutoscale = false // 항상 같은 크기로 보이도록
                    setCustomImageAnchor(0.5f, 1.0f)
                }
                binding.mapView.addPOIItem(curPositionMarker)
                binding.mapView.setZoomLevel(1, true)
            } else {
                curPositionMarker?.mapPoint = mapPoint
            }
        }
        viewModel.nearByStores.observe(viewLifecycleOwner) {
            binding.mapView.removePOIItems(nearByStoreMarkers)
            nearByStoreMarkers = it.makeStoreInfoToMarkers()
            binding.mapView.addPOIItems(nearByStoreMarkers)
        }
    }

    private fun setMinDistance() {
        val bounds = binding.mapView.mapPointBounds
        val bl = bounds.bottomLeft.mapPointGeoCoord
        val tr = bounds.topRight.mapPointGeoCoord
        viewModel.setVisibleMapDistance(
            GeoPoint(bl.latitude, bl.longitude),
            GeoPoint(tr.latitude, tr.longitude),
        )
    }

    private fun List<MapStoreInfo>.makeStoreInfoToMarkers(): Array<MapPOIItem> {
        return groupBy { it.geoPoint }.map { samePointStores ->
            val mapPoint = MapPoint.mapPointWithGeoCoord(
                samePointStores.key.latitude,
                samePointStores.key.longitude,
            )
            MapPOIItem().apply {
                itemName = samePointStores.value.joinToString()
                markerType = MapPOIItem.MarkerType.YellowPin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
                this.mapPoint = mapPoint
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = R.drawable.ic_map_open_store_24
                isCustomImageAutoscale = false // 항상 같은 크기로 보이도록
                setCustomImageAnchor(0.5f, 1.0f)
            }
        }.toTypedArray()
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()

        if (isAllowedLocationPermission().not()) return
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper(),
        )
    }

    override fun onStop() {
        super.onStop()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun isAllowedLocationPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

    private fun checkLocationPermission() {
        if (checkLocationService().not()) {
            showToast(R.string.map_location_service_turn_on_require_message)
        }

        // 둘 중에 한 개도 부여된 것이 없다면,,
        if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            requireActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showSnackBar(R.string.map_location_permission_require_message) { navigateToPermissionSetting() }
            } else {
                locationResultLauncher.launch(REQUIRE_LOCATION_PERMISSIONS)
            }
        } else {
            if (requireActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
                showSnackBar(R.string.map_location_permission_upgrade_require_message) { navigateToPermissionSetting() }
            }
        }
    }

    private fun showSnackBar(@StringRes messageId: Int, action: () -> Unit) {
        binding.root.showSnackbar(messageId) { action() }
    }

    private fun navigateToPermissionSetting() {
        Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", requireActivity().packageName, null)
            startActivity(this)
        }
    }

    // GPS가 켜져있는지 확인
    private fun checkLocationService(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun showToast(@StringRes messageId: Int) {
        Toaster.showShort(requireContext(), requireContext().getString(messageId))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        curPositionMarker = null
    }

    companion object {
        private val REQUIRE_LOCATION_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    }
}
