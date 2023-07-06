package com.mibaldi.rickymorty.testshared

import com.mibaldi.rickymorty.domain.Info
import com.mibaldi.rickymorty.domain.Location
import com.mibaldi.rickymorty.domain.MyCharacter
import com.mibaldi.rickymorty.domain.Result

val sampleCharacter = MyCharacter(
    2,
"Morty Smith",
"Alive",
"Human",
    "",
"Male",
    Location( "Earth","https://rickandmortyapi.com/api/location/1"),
    Location("Earth","https://rickandmortyapi.com/api/location/20"),
"https://rickandmortyapi.com/api/character/avatar/2.jpeg",
    mutableListOf("https://rickandmortyapi.com/api/episode/1","https://rickandmortyapi.com/api/episode/2"),
"https://rickandmortyapi.com/api/character/2",
"2017-11-04T18:50:21.651Z"
)
val sampleInfo = Info(850,46,"https://rickandmortyapi.com/api/character?page=2",null)
val sampleResult =
    Result(
        sampleInfo,
        listOf(sampleCharacter)
    )
