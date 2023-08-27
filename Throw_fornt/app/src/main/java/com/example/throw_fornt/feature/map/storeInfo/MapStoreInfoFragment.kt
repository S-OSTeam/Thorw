package com.example.throw_fornt.feature.map.storeInfo

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.throw_fornt.databinding.FragmentMapStoreInfoBinding
import com.example.throw_fornt.feature.map.MapViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapStoreInfoFragment : BottomSheetDialogFragment() {
    private val parentViewModel: MapViewModel by viewModels({ requireParentFragment() })
    private val viewModel: MapStoreInfoViewModel by viewModels()

    private var _binding: FragmentMapStoreInfoBinding? = null
    private val binding: FragmentMapStoreInfoBinding
        get() = _binding!!

    private val adapter: MapStoreInfoAdapter by lazy {
        MapStoreInfoAdapter(viewModel::onStoreCall, viewModel::onNavigatorStart)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapStoreInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setupViewModel()
        setupStoreAdapter()
    }

    private fun setupViewModel() {
        viewModel.event.observe(viewLifecycleOwner) { handleEvent(it) }
    }

    private fun setupStoreAdapter() {
        binding.rvStoreInfo.addItemDecoration(MapStoreAdapterItemDecoration(convertDpToPx(15f)))
        binding.rvStoreInfo.adapter = adapter
        adapter.setStores(parentViewModel.searchedStores)
    }

    private fun convertDpToPx(dp: Float): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    private fun handleEvent(event: MapStoreInfoViewModel.Event) {
        when (event) {
            is MapStoreInfoViewModel.Event.Dismiss -> {
                dismiss()
            }

            is MapStoreInfoViewModel.Event.NavigateToCall -> {}

            is MapStoreInfoViewModel.Event.NavigateToDestination -> {}
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
