package models

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(val phone: String, val password: String)