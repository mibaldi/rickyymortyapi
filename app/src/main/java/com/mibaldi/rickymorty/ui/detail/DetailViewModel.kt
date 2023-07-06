package com.mibaldi.rickymorty.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mibaldi.rickymorty.domain.Error
import com.mibaldi.rickymorty.domain.MyCharacter
import com.mibaldi.rickymorty.usecases.GetCharacterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val getCharacterUseCase: GetCharacterUseCase): ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    fun getCharacter(id: Int){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            getCharacterUseCase.getCharacter(id)
                .fold(
                    ifLeft = {cause -> _state.update { it.copy(error = cause, loading = false) }},
                    ifRight = {result ->
                        _state.update { UiState(myCharacter = result) }
                    }
                )
            _state.value = _state.value.copy(loading = false)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val myCharacter: MyCharacter? = null,
        val error: Error? = null
    )
}