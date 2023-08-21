package com.example.throw_fornt.feature.home

import androidx.databinding.BindingAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

@BindingAdapter("onNavigationItemSelected")
fun BottomNavigationView.bindOnNavigationItemSelectedListener(
    listener: NavigationBarView.OnItemSelectedListener,
) {
    this.setOnItemSelectedListener(listener)
}
