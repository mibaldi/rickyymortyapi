package com.mibaldi.rickyymorty.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mibaldi.rickyymorty.domain.MyCharacter
import com.mibaldi.rickyymorty.domain.Info
import com.mibaldi.rickyymorty.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mibaldi.rickyymorty.domain.Error

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    private val _info = MutableStateFlow<Info?>(null)
    val info: StateFlow<Info?> = _info.asStateFlow()
    init {
        getCharaters(0)
    }

    fun getCharaters(page: Int,filter: Map<String,String>? = emptyMap()){
        viewModelScope.launch {
            val mapOfPage = mapOf(Pair("page", page.toString()))
            val map = filter?.let { mapOfPage + filter } ?: mapOfPage
            getCharactersUseCase.getCharacters(map)
                .fold(
                    ifLeft = {cause -> _state.update { it.copy(error = cause) }},
                    ifRight = {result ->
                        _state.update { UiState(myCharacters = result.results) }
                        _info.update { result.info }
                    }
                )
        }
    }
    data class UiState(
        val loading: Boolean = false,
        val myCharacters: List<MyCharacter>? = null,
        val error: Error? = null
    )

}