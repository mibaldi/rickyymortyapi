package com.mibaldi.rickyymorty.ui.main

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.rickyymorty.R
import com.mibaldi.rickyymorty.databinding.ViewCharacterBinding
import com.mibaldi.rickyymorty.domain.MyCharacter
import com.mibaldi.rickyymorty.ui.common.basicDiffUtil
import com.mibaldi.rickyymorty.ui.common.inflate

class CharacterAdapter(private val listener: (MyCharacter) -> Unit) :
    ListAdapter<MyCharacter, CharacterAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.view_character, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        holder.itemView.setOnClickListener { listener(product) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ViewCharacterBinding.bind(view)
        fun bind(product: MyCharacter) {
            binding.product = product
        }
    }

}