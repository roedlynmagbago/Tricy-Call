package com.example.nasugbutricycleapp.model

open class User(
    val userId: Int,
    var name: String,
    var username: String,
    var phoneNumber: String,
    private var password: String,
    var role: String
) {

    fun checkPassword(inputPassword: String): Boolean {
        return password == inputPassword
    }

    fun login() {
        println("$username logged in.")
    }

    fun logout() {
        println("$username logged out.")
    }

    fun updateProfile(newName: String, newPhoneNumber: String) {
        name = newName
        phoneNumber = newPhoneNumber
    }

    fun changePassword(newPassword: String): Boolean {
        return if (newPassword.length >= 6) {
            password = newPassword
            true
        } else {
            false
        }
    }
}