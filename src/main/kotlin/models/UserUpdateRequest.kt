package models

import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val password: String? = null,
    val age: Int? = null
)
