package models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int = 0,
    val name: String = "",
    val phone: String,
    val email: String = "",
    val password: String,
    val age: Int = 0
)