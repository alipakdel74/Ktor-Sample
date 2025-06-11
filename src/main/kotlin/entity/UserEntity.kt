package entity

import utils.formatPhoneNumber
import models.User
import models.UserResponse
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import table.Users

class UserEntity(id: EntityID<Int>) : IntEntity(id) {

    companion object : IntEntityClass<UserEntity>(Users)

    var name by Users.name
    var email by Users.email
    var password by Users.password
    var age by Users.age
    var phone by Users.phone

    fun toUser(): UserResponse = UserResponse(id.value, formatPhoneNumber(phone), name, email, age)
}