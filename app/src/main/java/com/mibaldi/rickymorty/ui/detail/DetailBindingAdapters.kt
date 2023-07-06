package com.mibaldi.rickymorty.ui.detail

import androidx.databinding.BindingAdapter
import com.mibaldi.rickymorty.domain.MyCharacter

@BindingAdapter("character")
fun CharacterDetailInfoView.updateCharacterDetails(character: MyCharacter?) {
    if (character != null) {
        setCharacter(character)
    }
}