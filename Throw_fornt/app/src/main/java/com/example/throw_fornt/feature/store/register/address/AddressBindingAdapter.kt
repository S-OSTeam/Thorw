package com.example.throw_fornt.feature.store.register.address

import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter

@BindingAdapter("setOnQueryTextListener")
fun SearchView.setOnQueryTextListener(searchAddress: (String?) -> Unit) {
    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            searchAddress(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }
    })
}