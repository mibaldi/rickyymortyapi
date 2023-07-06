package com.mibaldi.rickymorty.apptestshared

import com.mibaldi.rickymorty.data.server.RemoteCharacter
import com.mibaldi.rickymorty.data.server.RemoteInfo
import com.mibaldi.rickymorty.data.server.RemoteResult
import com.mibaldi.rickymorty.data.server.RemoteService

class FakeRemoteService(private val info: RemoteInfo,private val characters: List<RemoteCharacter> = emptyList()) :
    RemoteService {



    override suspend fun listOfCharacters(page: Int, options: Map<String, String>?) =
        RemoteResult(info,characters)


    override suspend fun character(id: Int) = characters.first()

}