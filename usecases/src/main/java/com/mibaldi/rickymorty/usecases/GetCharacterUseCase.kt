package com.mibaldi.rickymorty.usecases

import com.mibaldi.rickymorty.data.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend fun getCharacter(id:Int) = repository.getCharacter(id)
}