package com.example.nasugbutricycleapp.model

class Passenger(
    userId: Int,
    name: String,
    username: String,
    phoneNumber: String,
    password: String
) : User(
    userId = userId,
    name = name,
    username = username,
    phoneNumber = phoneNumber,
    password = password,
    role = "PASSENGER"
) {

    fun bookRide() {
        println("$name is booking a ride.")
    }

    fun setPickup(pickupLocation: String) {
        println("Pickup location: $pickupLocation")
    }

    fun setDestination(destination: String) {
        println("Destination: $destination")
    }

    fun trackDriver() {
        println("Tracking driver...")
    }

    fun cancelBooking() {
        println("Booking cancelled.")
    }

    fun rateDriver(rating: Int) {
        println("Driver rated: $rating")
    }

    fun reportDriver(reason: String) {
        println("Driver reported: $reason")
    }

    fun viewTripHistory() {
        println("Viewing trip history.")
    }
}