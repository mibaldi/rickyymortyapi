package com.mibaldi.rickymorty.ui.main

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mibaldi.rickymorty.domain.MyCharacter
import com.mibaldi.rickymorty.domain.Info
import com.mibaldi.rickymorty.usecases.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.mibaldi.rickymorty.domain.Error

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()
    private var lastFilterMap = mapOf<String,String>()

    private val _pages = MutableStateFlow(Pages())
    val pages: StateFlow<Pages> = _pages.asStateFlow()


    private var currentPage = 1
    private var isLastPage = false

    init {
        getCharacters(1)
    }

    fun getCharacters(page: Int, filter: Map<String,String>? = null){
        viewModelScope.launch {
            val generateFilters = generateFilters(filter)
            _state.value = _state.value.copy(loading = true)
            getCharactersUseCase.getCharacters(page,generateFilters)
                .fold(
                    ifLeft = {cause -> _state.update { it.copy(error = cause, loading = false) }},
                    ifRight = {result ->
                        _state.update { UiState(myCharacters = result.results) }
                        pagesGenerator(result.info)
                        _state.value = _state.value.copy(loading = false)
                    }
                )
        }
    }


    private fun pagesGenerator(infoResponse:Info){
        _pages.update { it.copy(prev = true,next = true) }

        if (infoResponse.prev.isNullOrEmpty()) {
            currentPage = 1
            _pages.update { it.copy(prev = false) }
        }
        if (infoResponse.next.isNullOrEmpty()){
            _pages.update { it.copy(next = false) }
        }
        if (infoResponse.next.isNullOrEmpty() && !infoResponse.prev.isNullOrEmpty()) {
            val page = getPage(infoResponse.prev,"0")
            currentPage = page.toInt() + 1
            isLastPage = true
            _pages.update { it.copy(next = false) }

        } else {
            val page = getPage(infoResponse.next,"2")
            currentPage = page.toInt() - 1
            isLastPage = false
        }
        _pages.update { it.copy(page= currentPage) }
    }

    private fun getPage(url: String?, defaultValue : String): String {
        return url?.toUri()?.getQueryParameter("page") ?: defaultValue
    }

    fun loadNextItems(){
        if (!isLastPage){
            getCharacters(currentPage+1)
        } else {
            _pages.update { it.copy(next = false) }
        }
    }
    fun loadPrevItems(){
        if (currentPage != 1){
            getCharacters(currentPage-1)
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
    var currentStatusFilter = ""
    var currentGenderFilter = ""
    fun addFilters(status:String,gender:String){
        currentStatusFilter = if (status== "all") {
            ""
        } else status
        currentGenderFilter = if (gender == "all") {
            ""
        } else gender

        val mapOfStatus = mapOf(Pair("status", currentStatusFilter))
        val mapOfGender = mapOf(Pair("gender", currentGenderFilter))
        lastFilterMap = lastFilterMap + mapOfStatus + mapOfGender
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