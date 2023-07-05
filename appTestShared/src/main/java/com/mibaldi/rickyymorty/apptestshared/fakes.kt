package com.mibaldi.rickyymorty.apptestshared

import com.mibaldi.rickyymorty.data.server.RemoteCharacter
import com.mibaldi.rickyymorty.data.server.RemoteInfo
import com.mibaldi.rickyymorty.data.server.RemoteResult
import com.mibaldi.rickyymorty.data.server.RemoteService

class FakeRemoteService(private val info: RemoteInfo,private val characters: List<RemoteCharacter> = emptyList()) :
    RemoteService {



    override suspend fun listOfCharacters(page: Int, options: Map<String, String>?) =
        RemoteResult(info,characters)


    override suspend fun character(id: Int) = characters.first()

}