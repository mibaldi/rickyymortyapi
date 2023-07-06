package com.mibaldi.rickymorty.data.datasource

import arrow.core.Either
import com.mibaldi.rickymorty.domain.Error
import com.mibaldi.rickymorty.domain.MyCharacter
import com.mibaldi.rickymorty.domain.Result

interface CharacterRemoteDataSource {
    suspend fun getCharacters(page: Int,options: Map<String,String>?): Either<Error, Result>
    suspend fun getCharacter(id: Int): Either<Error, MyCharacter>
}