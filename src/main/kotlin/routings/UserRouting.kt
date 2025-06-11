package routings

import entity.UserEntity
import utils.hashPassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.UserUpdateRequest
import org.jetbrains.exposed.sql.transactions.transaction
import table.Users
import utils.showMessage


fun Application.userRouting() {
    routing {
        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val phone = principal?.getClaim("phone", String::class) ?: ""
                val user = transaction {
                    UserEntity.find { Users.phone eq phone }.singleOrNull()
                }

                if (user != null) {
                    call.respond(user.toUser())
                } else {
                    showMessage(HttpStatusCode.NotFound, "User not found")
                }
            }

            put("/me/update") {
                val principal = call.principal<JWTPrincipal>()
                val phone = principal?.getClaim("phone", String::class)
                    ?: return@put call.respond(HttpStatusCode.Unauthorized)

                val input = call.receive<UserUpdateRequest>()

                val updated = transaction {
                    val user = UserEntity.find { Users.phone eq phone }.singleOrNull()

                    if (user == null) {
                        null
                    } else {
                        input.name?.let { user.name = it }
                        input.email?.let { user.email = it }
                        input.age?.let { user.age = it }
                        input.password?.takeIf { it.isNotBlank() }?.let {
                            user.password = hashPassword(it)
                        }
                        user.toUser()
                    }
                }

                if (updated != null) {
                    call.respond(updated)
                } else {
                    showMessage(HttpStatusCode.NotFound, "User not found")
                }
            }

            delete("/me/delete") {
                val principal = call.principal<JWTPrincipal>()
                val phone = principal?.getClaim("phone", String::class)
                    ?: return@delete call.respond(HttpStatusCode.Unauthorized)

                val deleted = transaction {
                    val user = UserEntity.find { Users.phone eq phone }.singleOrNull()
                    if (user != null) {
                        user.delete()
                        true
                    } else {
                        false
                    }
                }

                if (deleted) {
                    showMessage(HttpStatusCode.OK, "User deleted successfully.")
                } else {
                    showMessage(HttpStatusCode.NotFound, "User not found.")
                }
            }
        }
    }
}

