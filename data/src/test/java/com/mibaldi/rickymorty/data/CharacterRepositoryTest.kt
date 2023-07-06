package com.mibaldi.rickymorty.data

import com.mibaldi.rickymorty.data.datasource.CharacterRemoteDataSource
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

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
    fun getCharacters() {
    }

    @Test
    fun getCharacter() {
    }
}