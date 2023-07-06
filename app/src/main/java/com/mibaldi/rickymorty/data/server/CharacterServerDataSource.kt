package com.mibaldi.rickymorty.data.server

import arrow.core.Either
import com.mibaldi.rickymorty.data.datasource.CharacterRemoteDataSource
import com.mibaldi.rickymorty.data.tryCall
import com.mibaldi.rickymorty.domain.MyCharacter
import com.mibaldi.rickymorty.domain.Error
import com.mibaldi.rickymorty.domain.Info
import com.mibaldi.rickymorty.domain.Location
import com.mibaldi.rickymorty.domain.Result
import javax.inject.Inject

class CharacterServerDataSource @Inject constructor(private val remoteService: RemoteService): CharacterRemoteDataSource {
    override suspend fun getCharacters(page:Int,options: Map<String, String>?): Either<Error, Result> = tryCall {
        remoteService
            .listOfCharacters(page,options)
            .toDomainModel()
    }

    override suspend fun getCharacter(id: Int): Either<Error, MyCharacter> = tryCall{
        remoteService
            .character(id)
            .toDomainModel()
    }

    private fun RemoteResult.toDomainModel(): Result {
        return Result(info.toDomainModel(),results.map { it.toDomainModel() })
    }
    private fun RemoteInfo.toDomainModel(): Info{
        return Info(count,pages,next,prev)
    }
    private fun RemoteCharacter.toDomainModel(): MyCharacter{
        return MyCharacter(id,name,status,species,type,gender,origin.toDomainModel(),location.toDomainModel(),image,episode,url,created)
    }
    private fun RemoteLocation.toDomainModel(): Location {
        return Location(name,url)
    }

}