package com.mibaldi.rickymorty.domain

data class MyCharacter(
    val id: Int = 0,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
data class Location(
    val name: String,
    val url: String
)