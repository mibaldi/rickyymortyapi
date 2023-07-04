package com.mibaldi.rickyymorty.data.server

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RemoteService {

    @GET("character")
    suspend fun listOfCharacters(@Query("page") page: Int,@QueryMap options: Map<String,String>?):RemoteResult

    @GET("character/{id}")
    suspend fun character(@Path("id") id: Int):RemoteCharacter
}