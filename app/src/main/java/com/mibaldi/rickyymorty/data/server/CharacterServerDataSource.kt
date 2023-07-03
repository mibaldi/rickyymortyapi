package com.mibaldi.rickyymorty.data.server

import arrow.core.Either
import com.mibaldi.rickyymorty.data.datasource.CharacterRemoteDataSource
import com.mibaldi.rickyymorty.data.tryCall
import com.mibaldi.rickyymorty.domain.Character
import com.mibaldi.rickyymorty.domain.Error
import com.mibaldi.rickyymorty.domain.Info
import com.mibaldi.rickyymorty.domain.Location
import com.mibaldi.rickyymorty.domain.Result
import javax.inject.Inject

class CharacterServerDataSource @Inject constructor(private val remoteService: RemoteService): CharacterRemoteDataSource {
    override suspend fun getCharacters(options: Map<String, String>?): Either<Error, Result> = tryCall {
        remoteService
            .listOfCharacters(options)
            .toDomainModel()
    }

    private fun RemoteResult.toDomainModel(): Result {
        return Result(info.toDomainModel(),results.map { it.toDomainModel() })
    }
    private fun RemoteInfo.toDomainModel(): Info{
        return Info(count,pages,next,prev)
    }
    private fun RemoteCharacter.toDomainModel(): Character{
        return Character(id,name,status,species,type,gender,origin.toDomainModel(),location.toDomainModel(),image,episode,url,created)
    }
    private fun RemoteLocation.toDomainModel(): Location {
        return Location(name,url)
    }

}