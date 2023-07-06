package com.mibaldi.rickymorty.usecases

import com.mibaldi.rickymorty.data.CharacterRepository
import com.mibaldi.rickymorty.testshared.sampleInfo
import com.mibaldi.rickymorty.testshared.sampleResult
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito

class GetCharactersUseCaseTest {

    @Test
    fun getCharacters() = runBlocking {

        val characterRepository = Mockito.mock<CharacterRepository>()
        val requestCharactersUseCase = GetCharactersUseCase(characterRepository)

        requestCharactersUseCase.getCharacters(1,null)

        Mockito.verify(characterRepository).getCharacters(1,null)
    }
}