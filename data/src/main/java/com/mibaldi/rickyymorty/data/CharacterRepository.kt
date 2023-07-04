package com.mibaldi.rickyymorty.data

import arrow.core.Either
import com.mibaldi.rickyymorty.data.datasource.CharacterRemoteDataSource
import com.mibaldi.rickyymorty.domain.Error
import com.mibaldi.rickyymorty.domain.MyCharacter
import com.mibaldi.rickyymorty.domain.Result
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val remoteDataSource: CharacterRemoteDataSource
) {
    suspend fun getCharacters(page:Int,options:Map<String,String>?): Either<Error, Result> {
        return options?.let {
             remoteDataSource.getCharacters(page,options)
        } ?: remoteDataSource.getCharacters(page,emptyMap())

    }

    suspend fun getCharacter(id: Int): Either<Error,MyCharacter> {
        return remoteDataSource.getCharacter(id)
    }
}