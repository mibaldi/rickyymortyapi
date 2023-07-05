package com.mibaldi.rickyymorty.ui.main

import app.cash.turbine.test
import com.mibaldi.rickyymorty.apptestshared.buildRemoteCharacter
import com.mibaldi.rickyymorty.apptestshared.buildRemoteInfo
import com.mibaldi.rickyymorty.apptestshared.buildRemoteResult
import com.mibaldi.rickyymorty.apptestshared.buildRepositoryWith
import com.mibaldi.rickyymorty.data.server.RemoteCharacter
import com.mibaldi.rickyymorty.data.server.RemoteInfo
import com.mibaldi.rickyymorty.data.server.RemoteResult
import com.mibaldi.rickyymorty.testrules.CoroutinesTestRule
import com.mibaldi.rickyymorty.usecases.GetCharactersUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.assertEquals
import com.mibaldi.rickyymorty.ui.main.MainViewModel.UiState
@ExperimentalCoroutinesApi
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