package com.example.throw_fornt.feature.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.example.throw_fornt.R
import com.example.throw_fornt.databinding.ActivityHomeBinding
import com.example.throw_fornt.feature.map.MapFragment
import com.example.throw_fornt.feature.myPage.MyPageFragment
import com.example.throw_fornt.util.common.BindingActivity

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val viewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel.currentFragmentType.observe(this) {
            changeFragment(it)
        }
    }

    private fun changeFragment(fragmentType: FragmentType) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            supportFragmentManager.fragments.forEach(::hide)
            supportFragmentManager.findFragmentByTag(fragmentType.tag)?.let {
                show(it)
            } ?: run { add(R.id.fcv_container, createFragment(fragmentType), fragmentType.tag) }
        }
    }

    private fun createFragment(fragmentType: FragmentType): Fragment {
        return when (fragmentType) {
            FragmentType.MAP -> MapFragment()
            FragmentType.MY_PAGE -> MyPageFragment()
        }
    }
}
