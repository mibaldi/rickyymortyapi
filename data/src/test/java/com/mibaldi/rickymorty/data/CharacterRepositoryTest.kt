package com.mibaldi.rickymorty.data

import arrow.core.Either
import com.mibaldi.rickymorty.data.datasource.CharacterRemoteDataSource
import com.mibaldi.rickymorty.testshared.sampleResult
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class CharacterRepositoryTest {
    @Mock
    lateinit var remoteDataSource: CharacterRemoteDataSource

    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setUp() {
        characterRepository = CharacterRepository(remoteDataSource)
    }
    @Test
    fun getCharacters() = runBlocking{
        whenever(remoteDataSource.getCharacters(any(), any())).thenReturn(Either.Right(sampleResult))
        val result = characterRepository.getCharacters(1,null)
        assertEquals(Either.Right(sampleResult), result)
    }

    @Test
    fun getCharacter() = runBlocking{
        whenever(remoteDataSource.getCharacter(any())).thenReturn(Either.Right(sampleResult.results.first()))
        val result = characterRepository.getCharacter(1)
        assertEquals(Either.Right(sampleResult.results.first()), result)
    }
}