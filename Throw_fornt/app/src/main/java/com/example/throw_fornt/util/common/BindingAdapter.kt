package com.example.throw_fornt.util.common

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class BindingAdapter {
    @BindingAdapter("imageUrl")
    fun ImageView.setImageUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .into(this)
        }
    }

    @BindingAdapter("imageUri")
    fun ImageView.setImageUrl(uri: Uri?) {
        uri?.let {
            Glide.with(context)
                .load(it)
                .into(this)
        }
    }

    @BindingAdapter("isVisible")
    fun View.isVisible(isVisible: Boolean) {
        this.isVisible = isVisible
    }

    @BindingAdapter("setTextOrEmpty")
    fun TextView.setTextOrEmpty(text: String?) {
        this.text = text ?: ""
    }
}