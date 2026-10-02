package com.example.nasugbutricycleapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.nasugbutricycleapp.repository.UserRepository
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import com.example.nasugbutricycleapp.service.AuthService

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun testRegisterAndLogin() {

        val registered = UserRepository.register(
            name = "Test Passenger",
            username = "testpassenger",
            phoneNumber = "09123456789",
            password = "password123"
        )

        assertTrue(registered)

        val user = UserRepository.login(
            username = "testpassenger",
            password = "password123"
        )

        assertNotNull(user)
    }

    @Test
    fun testWrongPassword() {

        UserRepository.register(
            name = "Passenger Two",
            username = "passenger2",
            phoneNumber = "09987654321",
            password = "mypassword"
        )

        val user = UserRepository.login(
            username = "passenger2",
            password = "wrongpassword"
        )

        assertNull(user)
    }

    @Test
    fun testAuthService() {

        val result = AuthService.register(
            name = "Sean",
            username = "sean123",
            phoneNumber = "09123456789",
            password = "password123",
            confirmPassword = "password123"
        )

        assertTrue(result == "Registration successful.")

        val loginSuccess = AuthService.login(
            username = "sean123",
            password = "password123"
        )

        assertTrue(loginSuccess)
        assertNotNull(AuthService.getCurrentUser())
        assertTrue(AuthService.isLoggedIn())
        AuthService.logout()
        assertNull(AuthService.getCurrentUser())
    }
}