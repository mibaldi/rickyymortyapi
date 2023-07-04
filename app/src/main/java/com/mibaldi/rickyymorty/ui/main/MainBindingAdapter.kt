package com.mibaldi.rickyymorty.ui.main

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.rickyymorty.domain.MyCharacter

@BindingAdapter("items")
fun RecyclerView.setItems(products: List<MyCharacter>?) {
    if (products != null) {
        (adapter as? CharacterAdapter)?.submitList(products)
    }
}
@BindingAdapter("pageVisible")
fun View.setVisible(visible: Boolean?) {
    visibility = if (visible == true) View.VISIBLE else View.INVISIBLE
}