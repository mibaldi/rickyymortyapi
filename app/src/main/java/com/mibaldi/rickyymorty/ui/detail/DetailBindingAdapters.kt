package com.mibaldi.rickyymorty.ui.detail

import androidx.databinding.BindingAdapter
import com.mibaldi.rickyymorty.domain.MyCharacter

@BindingAdapter("character")
fun CharacterDetailInfoView.updateCharacterDetails(character: MyCharacter?) {
    if (character != null) {
        setCharacter(character)
    }
}