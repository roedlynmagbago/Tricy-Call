package com.example.nasugbutricycleapp.repository

import com.example.nasugbutricycleapp.model.User

object UserRepository {

    private val users = mutableListOf<User>()

    fun register(
        name: String,
        username: String,
        phoneNumber: String,
        password: String,
        role: String = "PASSENGER"
    ): Boolean {

        if (usernameExists(username)) {
            return false
        }

        val user = User(
            userId = users.size + 1,
            name = name,
            username = username,
            phoneNumber = phoneNumber,
            password = password,
            role = role
        )

        users.add(user)
        return true
    }

    fun login(username: String, password: String): User? {
        return users.find { user ->
            user.username.equals(username, ignoreCase = true) &&
                    user.checkPassword(password)
        }
    }

    fun usernameExists(username: String): Boolean {
        return users.any { user ->
            user.username.equals(username, ignoreCase = true)
        }
    }

    fun getUserById(userId: Int): User? {
        return users.find { user ->
            user.userId == userId
        }
    }

    fun getAllUsers(): List<User> {
        return users.toList()
    }
}