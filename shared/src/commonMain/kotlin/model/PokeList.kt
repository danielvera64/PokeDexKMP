package com.zakumi.model

import kotlinx.serialization.Serializable

@Serializable
data class PokeList(
    val next: String? = "",
    val previous: String? = "",
    val results: List<Pokemon> = emptyList()
)