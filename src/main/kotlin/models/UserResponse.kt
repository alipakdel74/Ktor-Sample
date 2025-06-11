package models

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(val id: Int = 0, val phone: String, val name: String?, val email: String?, val age: Int?)