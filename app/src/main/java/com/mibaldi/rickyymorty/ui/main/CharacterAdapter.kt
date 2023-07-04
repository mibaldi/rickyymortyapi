package com.mibaldi.rickyymorty.ui.main

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
        fun bind(character: MyCharacter) {
            binding.character = character
            val shape =when(character.status){
                "Alive" -> Color.parseColor("#416CDA31")
                "Dead" -> Color.parseColor("#41F64232")
                "unknown" -> Color.parseColor("#41000000")
                else -> Color.parseColor("#416CDA31")
            }

            binding.characterCover.setColorFilter(shape)
            binding.characterName.setBackgroundColor(shape)
        }
    }

}