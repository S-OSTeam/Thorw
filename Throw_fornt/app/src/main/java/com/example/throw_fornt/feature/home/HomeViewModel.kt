package com.example.throw_fornt.feature.home

import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.throw_fornt.R

class HomeViewModel : ViewModel() {
    private val _currentFragmentType: MutableLiveData<FragmentType> =
        MutableLiveData(FragmentType.MAP)
    val currentFragmentType: LiveData<FragmentType>
        get() = _currentFragmentType

    fun setCurrentFragment(item: MenuItem): Boolean {
        val menuItemId = item.itemId
        val pageType = getPageType(menuItemId)
        changeCurrentFragmentType(pageType)

        return true
    }

    private fun getPageType(menuItemId: Int): FragmentType {
        return when (menuItemId) {
            R.id.menu_item_map -> FragmentType.MAP
            R.id.menu_item_mypage -> FragmentType.MY_PAGE
            else -> throw IllegalArgumentException("Not found menu item")
        }
    }

    private fun changeCurrentFragmentType(fragmentType: FragmentType) {
        if (currentFragmentType.value == fragmentType) return

        _currentFragmentType.value = fragmentType
    }
}
