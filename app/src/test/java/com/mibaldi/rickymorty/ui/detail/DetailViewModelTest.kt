package com.mibaldi.rickymorty.ui.detail

import app.cash.turbine.test
import arrow.core.Either
import com.mibaldi.rickymorty.testrules.CoroutinesTestRule
import com.mibaldi.rickymorty.testshared.sampleCharacter
import com.mibaldi.rickymorty.usecases.GetCharacterUseCase
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getCharacterUseCase: GetCharacterUseCase


    private lateinit var vm: DetailViewModel

    private val character = sampleCharacter.copy(id = 2)

    @Test
    fun `UI is updated with the character on start`() = runTest {
        vm = buildViewModel()
        vm.getCharacter(1)
        vm.state.test {
            assertEquals(DetailViewModel.UiState(loading = false), awaitItem())
            assertEquals(DetailViewModel.UiState(loading = true), awaitItem())
            assertEquals(DetailViewModel.UiState(myCharacter = character), awaitItem())

            cancel()
        }
    }

    private fun buildViewModel(): DetailViewModel {
        runBlocking {
            whenever(getCharacterUseCase.getCharacter(any())).thenReturn(Either.Right(character))
        }
        return DetailViewModel(getCharacterUseCase)
    }
}