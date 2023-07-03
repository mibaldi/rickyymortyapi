package com.mibaldi.rickyymorty.usecases

import arrow.core.Either
import com.mibaldi.rickyymorty.data.CharacterRepository
import com.mibaldi.rickyymorty.domain.Error
import com.mibaldi.rickyymorty.domain.Result
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repository: CharacterRepository){
    suspend fun getCharacters(options:Map<String,String>?) : Either<Error,Result> {
        return repository.getCharacters(options)
    }
}