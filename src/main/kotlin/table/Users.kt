package table

import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {
    val name = varchar("name", 50).nullable()
    val email = varchar("email", 50).nullable()
    val password = varchar("password", 64) // hashed
    val age = integer("age").nullable()
    val phone = varchar("phone",15).uniqueIndex()
}