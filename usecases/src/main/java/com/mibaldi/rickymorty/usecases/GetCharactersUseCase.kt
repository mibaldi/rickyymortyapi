package com.mibaldi.rickymorty.usecases

import arrow.core.Either
import com.mibaldi.rickymorty.data.CharacterRepository
import com.mibaldi.rickymorty.domain.Error
import com.mibaldi.rickymorty.domain.Result
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository){
    suspend fun getCharacters(page: Int,options:Map<String,String>?) : Either<Error,Result> {
        return repository.getCharacters(page,options)
    }
}