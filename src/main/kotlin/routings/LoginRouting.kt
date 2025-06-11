package routings

import JwtConfig
import utils.formatPhoneNumber
import entity.UserEntity
import utils.hashPassword
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import models.User
import models.UserLogin
import org.jetbrains.exposed.sql.transactions.transaction
import table.Users
import utils.showMessage
import utils.verifyPassword


fun Application.loginRouting() {
    routing {
        // add user
        post("/user/add") {
            val user = call.receive<User>()

            log.error(user.toString())
            if (user.phone.isEmpty()){
                showMessage(HttpStatusCode.Conflict, "invalid Phone")
                return@post
            }

            if (user.password.isEmpty()){
                showMessage(HttpStatusCode.Conflict, "invalid Password")
                return@post
            }

            val existingUser = transaction {
                UserEntity.find { Users.phone eq formatPhoneNumber(user.phone) }.singleOrNull()
            }

            if (existingUser != null) {
                showMessage(HttpStatusCode.Conflict, "User already exists")
                return@post
            }

            val hashedPassword = hashPassword(user.password)

            transaction {
                UserEntity.new {
                    user.name.let { name = it }
                    phone = formatPhoneNumber(user.phone)
                    password = hashedPassword
                    user.email.let { email = it }
                    user.age.let { age = it }
                }
            }

            showMessage(HttpStatusCode.Created, "User created successfully")
        }

        post("/user/login") {
            val loginData = call.receive<UserLogin>()

            val user = transaction {
                UserEntity.find { Users.phone eq formatPhoneNumber(loginData.phone) }.singleOrNull()
            }

            if (user == null || !verifyPassword(loginData.password, user.password)) {
                showMessage(HttpStatusCode.Conflict, "Invalid credentials")
                return@post
            }

            val token = JwtConfig.generateToken(user.phone)
            call.respond(mapOf("token" to token))
        }
    }
}

