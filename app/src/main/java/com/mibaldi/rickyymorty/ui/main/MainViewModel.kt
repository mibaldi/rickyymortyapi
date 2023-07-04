package com.mibaldi.rickyymorty.ui.main

import android.util.Log
import android.view.View
import androidx.core.net.toUri
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
    private var lastFilterMap = mapOf<String,String>()

    private val _pages = MutableStateFlow<Pages>(Pages())
    val pages: StateFlow<Pages> = _pages.asStateFlow()


    private var currentPage = 1
    var isLastPage = false

    init {
        getCharaters(1)
    }

    fun getCharaters(page: Int,filter: Map<String,String>? = null){
        viewModelScope.launch {
            val generateFilters = generateFilters(filter)
            _state.value = _state.value.copy(loading = true)
            getCharactersUseCase.getCharacters(page,generateFilters)
                .fold(
                    ifLeft = {cause -> _state.update { it.copy(error = cause) }},
                    ifRight = {result ->
                        _state.update { UiState(myCharacters = result.results) }
                        pagesGenerator(result.info)
                        _state.value = _state.value.copy(loading = false)
                    }
                )
        }
    }


    private fun pagesGenerator(infoResponse:Info){
        Log.d("INFO",infoResponse.toString())
        _pages.update { it.copy(prev = true,next = true) }

        if (infoResponse.prev.isNullOrEmpty()) {
            currentPage = 1
            _pages.update { it.copy(prev = false) }
        }
        if (infoResponse.next.isNullOrEmpty()){
            _pages.update { it.copy(next = false) }
        }
        if (infoResponse.next.isNullOrEmpty() && !infoResponse.prev.isNullOrEmpty()) {
            val page = infoResponse.prev?.toUri()?.getQueryParameter("page") ?: "0"
            currentPage = page.toInt() + 1
            isLastPage = true
            _pages.update { it.copy(next = false) }

        } else {
            val page = infoResponse.next?.toUri()?.getQueryParameter("page") ?: "2"
            currentPage = page.toInt() - 1
            isLastPage = false
        }
        _pages.update { it.copy(page= currentPage) }
    }

    fun loadNextItems(){
        if (!isLastPage){
            getCharaters(currentPage+1)
        } else {
            _pages.update { it.copy(next = false) }
        }
    }
    fun loadPrevItems(){
        if (currentPage != 1){
            getCharaters(currentPage-1)
        } else {
            _pages.update { it.copy(prev = false) }
        }
    }
    private fun generateFilters(options:Map<String,String>?): Map<String,String>{
        if (options != null){
            lastFilterMap = options
        }
        return lastFilterMap
    }
    data class UiState(
        val loading: Boolean = false,
        val myCharacters: List<MyCharacter>? = null,
        val error: Error? = null
    )
    data class Pages(
        val prev:Boolean = false,
        val next: Boolean = false,
        val page: Int = 1
    )

}