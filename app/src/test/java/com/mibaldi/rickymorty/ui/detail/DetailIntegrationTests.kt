package com.mibaldi.rickymorty.ui.detail

import app.cash.turbine.test
import com.mibaldi.rickymorty.apptestshared.buildRemoteResult
import com.mibaldi.rickymorty.apptestshared.buildRepositoryWith
import com.mibaldi.rickymorty.data.server.RemoteResult
import com.mibaldi.rickymorty.testrules.CoroutinesTestRule
import com.mibaldi.rickymorty.usecases.GetCharacterUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTests {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the character on start`() = runTest {
        val vm = buildViewModelWith(
            buildRemoteResult(2,3)
        )
        vm.getCharacter(2)
        vm.state.test {
            Assert.assertEquals(DetailViewModel.UiState(loading = false), awaitItem())
            Assert.assertEquals(DetailViewModel.UiState(loading = true), awaitItem())
            Assert.assertEquals(2, awaitItem().myCharacter!!.id)
            cancel()
        }
    }


    private fun buildViewModelWith(
        remoteData: RemoteResult = buildRemoteResult(2,3)
    ): DetailViewModel {
        val characterRepository = buildRepositoryWith(remoteData.info,remoteData.results)
        val getCharacterUseCase = GetCharacterUseCase(characterRepository)
        return DetailViewModel(getCharacterUseCase)
    }
}