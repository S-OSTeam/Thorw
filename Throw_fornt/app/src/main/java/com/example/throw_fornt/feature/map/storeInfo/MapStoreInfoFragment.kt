package com.example.throw_fornt.feature.map.storeInfo

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
    }

    private fun setupViewModel() {
        viewModel.event.observe(viewLifecycleOwner) { handleEvent(it) }
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
