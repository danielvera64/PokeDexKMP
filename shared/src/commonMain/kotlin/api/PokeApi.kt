package com.zakumi.api

import com.zakumi.Endpoints
import com.zakumi.Params
import com.zakumi.Urls
import com.zakumi.model.PokeList
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.Url
import kotlinx.serialization.json.Json

class PokeApi {

    private val client = HttpClient()

    suspend fun getList(offset: Int = 0, limit: Int = 20): PokeList {
        val result: String = client.get {
            url(Urls.PokeApiUrl + Endpoints.pokemon)
            parameter(Params.offset, offset)
            parameter(Params.limit, limit)
        }
        return Json.nonstrict.parse(PokeList.serializer(), result)
    }
}