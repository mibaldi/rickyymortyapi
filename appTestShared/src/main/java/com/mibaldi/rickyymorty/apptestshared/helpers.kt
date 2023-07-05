package com.mibaldi.rickyymorty.apptestshared

import com.mibaldi.rickyymorty.data.CharacterRepository
import com.mibaldi.rickyymorty.data.server.CharacterServerDataSource
import com.mibaldi.rickyymorty.data.server.RemoteCharacter
import com.mibaldi.rickyymorty.data.server.RemoteInfo
import com.mibaldi.rickyymorty.data.server.RemoteLocation
import com.mibaldi.rickyymorty.data.server.RemoteResult

fun buildRepositoryWith(
    remoteInfo: RemoteInfo,
    remoteData: List<RemoteCharacter>
): CharacterRepository {
    val remoteDataSource = CharacterServerDataSource(FakeRemoteService(remoteInfo,remoteData))
    return CharacterRepository( remoteDataSource)
}

fun buildRemoteResult(vararg id: Int) = RemoteResult(buildRemoteInfo(), buildRemoteCharacter(id))
fun buildRemoteInfo() =
    RemoteInfo(
        826,42,"https://rickandmortyapi.com/api/character?page=2",null
    )
fun buildRemoteCharacter( id: IntArray) = id.map {
    RemoteCharacter(
        it,
        "Morty Smith $it",
        "Alive",
        "Human",
        "",
        "Male",
        RemoteLocation( "Earth","https://rickandmortyapi.com/api/location/1"),
        RemoteLocation("Earth","https://rickandmortyapi.com/api/location/20"),
        "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        mutableListOf("https://rickandmortyapi.com/api/episode/1","https://rickandmortyapi.com/api/episode/2"),
        "https://rickandmortyapi.com/api/character/2",
        "2017-11-04T18:50:21.651Z"
    )
}

