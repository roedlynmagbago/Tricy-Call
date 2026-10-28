package com.example.nasugbutricycleapp.model

data class AvailableBooking(
    val bookingId: String = "",
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
