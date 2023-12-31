package com.mibaldi.rickymorty.ui.main

import arrow.core.Either
import com.mibaldi.rickymorty.testrules.CoroutinesTestRule
import com.mibaldi.rickymorty.testshared.sampleCharacter
import com.mibaldi.rickymorty.testshared.sampleResult
import com.mibaldi.rickymorty.usecases.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var vm: MainViewModel
    private val result = sampleResult.copy(results = listOf(sampleCharacter.copy(id = 1)))

    @Test
    fun `Characters requested when UI screen starts`() = runTest {
        vm = buildViewModel()
        runCurrent()
        verify(getCharactersUseCase).getCharacters(1, emptyMap())
    }
    private fun buildViewModel(): MainViewModel {
        runBlocking {
            whenever((getCharactersUseCase.getCharacters(any(), any()))).thenReturn(Either.Right(result))
        }
        return MainViewModel(getCharactersUseCase)
    }
}