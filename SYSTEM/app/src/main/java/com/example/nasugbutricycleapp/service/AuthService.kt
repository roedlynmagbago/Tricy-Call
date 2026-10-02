package com.example.nasugbutricycleapp.service

import com.example.nasugbutricycleapp.model.User
import com.example.nasugbutricycleapp.repository.UserRepository

object AuthService {

    private var currentUser: User? = null

    fun register(
        name: String,
        username: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ): String {

        if (name.isBlank() ||
            username.isBlank() ||
            phoneNumber.isBlank() ||
            password.isBlank()
        ) {
            return "Please fill in all fields."
        }

        if (password != confirmPassword) {
            return "Passwords do not match."
        }

        if (password.length < 6) {
            return "Password must be at least 6 characters."
        }

        if (UserRepository.usernameExists(username)) {
            return "Username already exists."
        }

        val registered = UserRepository.register(
            name = name,
            username = username,
            phoneNumber = phoneNumber,
            password = password
        )

        return if (registered) {
            "Registration successful."
        } else {
            "Registration failed."
        }
    }

    fun login(username: String, password: String): Boolean {

        if (username.isBlank() || password.isBlank()) {
            return false
        }

        val user = UserRepository.login(
            username = username,
            password = password
        )

        return if (user != null) {
            currentUser = user
            user.login()
            true
        } else {
            false
        }
    }

    fun logout() {
        currentUser?.logout()
        currentUser = null
    }

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun isLoggedIn(): Boolean {
        return currentUser != null
    }
}