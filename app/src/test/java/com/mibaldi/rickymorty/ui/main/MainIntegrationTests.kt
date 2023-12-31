package com.mibaldi.rickymorty.ui.main

import android.net.Uri
import app.cash.turbine.test
import com.mibaldi.rickymorty.apptestshared.buildRemoteResult
import com.mibaldi.rickymorty.apptestshared.buildRepositoryWith
import com.mibaldi.rickymorty.data.server.RemoteCharacter
import com.mibaldi.rickymorty.data.server.RemoteInfo
import com.mibaldi.rickymorty.testrules.CoroutinesTestRule
import com.mibaldi.rickymorty.ui.main.MainViewModel.UiState
import com.mibaldi.rickymorty.usecases.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server `() = runTest {
        val remoteData = buildRemoteResult(4, 5, 6)
        val vm = buildViewModelWith(
            remoteInfo = remoteData.info,
            remoteCharacter = remoteData.results
        )

        vm.state.test {
            assertEquals(UiState(myCharacters = null, loading = false), awaitItem())
            assertEquals(UiState(myCharacters = null, loading = true), awaitItem())

            val characters = awaitItem().myCharacters!!
            assertEquals("Morty Smith 4", characters[0].name)
            assertEquals("Morty Smith 5", characters[1].name)
            assertEquals("Morty Smith 6", characters[2].name)
            cancel()
        }
    }

    private fun buildViewModelWith(
        remoteInfo: RemoteInfo,
        remoteCharacter: List<RemoteCharacter>
    ): MainViewModel {

        val characterRepository = buildRepositoryWith(remoteInfo,remoteCharacter)
        val getCharactersUseCase = GetCharactersUseCase(characterRepository)
        return MainViewModel(getCharactersUseCase)
    }
}