package com.zakumi.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon (val id: Int = 0,
                    val name: String,
                    val sprite: String = "")