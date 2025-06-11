package routings

import entity.UserEntity
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import utils.showMessage


fun Application.adminRouting() {
    routing {
        // check connected to server
        get("/") {
            showMessage(HttpStatusCode.OK, "connected")
        }

        route("/admin/users/{adminPass}") {
            get {
                val adminPass = call.parameters["adminPass"]
                if (adminPass != "123456") {
                    showMessage(HttpStatusCode.NotFound, "admin not found")
                    return@get
                }
                val query = call.queryParameters["id"]
                if (query == null) {
                    val users = transaction {
                        UserEntity.all().map { it.toUser() }
                    }
                    call.respond(users)
                } else {
                    val user = transaction {
                        query.toIntOrNull()?.let { id ->
                            UserEntity.findById(id)
                        }?.toUser()
                    }
                    if (user == null) {
                        showMessage(HttpStatusCode.NotFound, "User not found")
                        return@get
                    }

                    call.respond(user)
                }
            }
        }
    }
}

