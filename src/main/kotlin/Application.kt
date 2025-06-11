package com.example

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import routings.loginRouting
import routings.adminRouting
import routings.userRouting
import table.Users


fun main() {
    embeddedServer(Netty, port = 8080) {
        install()
        configureDatabase()
        module()
    }.start(wait = true)
}

fun Application.module() {
    adminRouting()
    loginRouting()
    userRouting()
}

fun Application.install() {
    install(ContentNegotiation) {
        json()
    }
    install(CallLogging)
    install(Authentication) {
        jwt("auth-jwt") {
            verifier(JwtConfig.verifier)
            validate {
                JWTPrincipal(it.payload)
            }
        }
    }
}

fun configureDatabase() {
    Database.connect("jdbc:sqlite:sample.db", driver = "org.sqlite.JDBC")
    transaction {
        SchemaUtils.create(Users)
    }
}