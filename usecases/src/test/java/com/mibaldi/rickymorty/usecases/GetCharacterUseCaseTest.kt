package com.mibaldi.rickymorty.usecases

import com.mibaldi.rickymorty.data.CharacterRepository
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class GetCharacterUseCaseTest {

    @Test
    fun getCharacter()  = runBlocking {

        val characterRepository = Mockito.mock<CharacterRepository>()
        val requestCharacterUseCase = GetCharacterUseCase(characterRepository)

        requestCharacterUseCase.getCharacter(1)

        Mockito.verify(characterRepository).getCharacter(1)
    }
}