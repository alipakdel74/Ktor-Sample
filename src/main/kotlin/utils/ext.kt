package utils

import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun hashPassword(password: String): String {
    return Base64.getEncoder().encodeToString(password.toByteArray()) // فقط برای تست، نه امن!
}

fun verifyPassword(input: String, hashed: String): Boolean {
    return hashPassword(input) == hashed
}

suspend fun RoutingContext.showMessage(status: HttpStatusCode, message: String) {
    call.respond(status, mapOf("message" to message))
}

fun formatPhoneNumber(phone: String): String {
    val cleaned = phone.trim().replace(" ", "").replace("-", "")
    return when {
        cleaned.startsWith("+98") -> cleaned
        cleaned.startsWith("0098") -> "+98" + cleaned.removePrefix("0098")
        cleaned.startsWith("0") -> "+98" + cleaned.removePrefix("0")
        else -> "+98$cleaned"
    }
}