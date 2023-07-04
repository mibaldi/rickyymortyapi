package com.mibaldi.rickyymorty.usecases

import com.mibaldi.rickyymorty.data.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend fun getCharacter(id:Int) = repository.getCharacter(id)
}