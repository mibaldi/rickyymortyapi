package com.mibaldi.rickymorty.ui.main

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mibaldi.rickymorty.R
import com.mibaldi.rickymorty.databinding.ViewCharacterBinding
import com.mibaldi.rickymorty.domain.MyCharacter
import com.mibaldi.rickymorty.ui.common.basicDiffUtil
import com.mibaldi.rickymorty.ui.common.inflate


class CharacterAdapter(private val listener: (MyCharacter) -> Unit) :
    ListAdapter<MyCharacter, CharacterAdapter.ViewHolder>(basicDiffUtil { old, new -> old.id == new.id }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = parent.inflate(R.layout.view_character, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
        holder.itemView.setOnClickListener { listener(character) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val context: Context = view.context
        private val binding = ViewCharacterBinding.bind(view)
        fun bind(character: MyCharacter) {

            binding.character = character
            val shape =when(character.status){
                "Alive" -> ContextCompat.getColor(context,R.color.alive)
                "Dead" -> ContextCompat.getColor(context,R.color.dead)
                "unknown" -> ContextCompat.getColor(context,R.color.unknown)
                else -> ContextCompat.getColor(context,R.color.alive)
            }

            binding.characterCover.setColorFilter(shape)
            binding.characterName.setBackgroundColor(shape)
        }
    }

}