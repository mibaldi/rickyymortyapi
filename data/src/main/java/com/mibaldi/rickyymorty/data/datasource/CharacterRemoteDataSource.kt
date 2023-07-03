package com.mibaldi.rickyymorty.data.datasource

import arrow.core.Either
import com.mibaldi.rickyymorty.domain.Error
import com.mibaldi.rickyymorty.domain.Result

interface CharacterRemoteDataSource {
    suspend fun getCharacters(options: Map<String,String>?): Either<Error, Result>
}