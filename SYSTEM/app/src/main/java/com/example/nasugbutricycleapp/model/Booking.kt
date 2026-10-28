package com.example.nasugbutricycleapp.model

data class Booking(
    val bookingId: String = "",
    val passengerId: String = "",
    val passengerPhone: String = "",
    val pickupLocation: String = "",
    val pickupLatitude: Double = 0.0,
    val pickupLongitude: Double = 0.0,
    val destination: String = "",
    val destinationLatitude: Double = 0.0,
    val destinationLongitude: Double = 0.0,
    val driverId: String = "",
    val status: String = "PENDING",
    val createdAt: Long = System.currentTimeMillis()
)
