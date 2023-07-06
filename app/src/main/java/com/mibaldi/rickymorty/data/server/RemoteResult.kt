package com.mibaldi.rickymorty.data.server

data class RemoteResult(
    val info: RemoteInfo,
    val results: List<RemoteCharacter>
)
data class RemoteInfo(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)
data class RemoteCharacter(
    val id: Int = 0,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: RemoteLocation,
    val location: RemoteLocation,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
data class RemoteLocation(
    val name: String,
    val url: String
)


