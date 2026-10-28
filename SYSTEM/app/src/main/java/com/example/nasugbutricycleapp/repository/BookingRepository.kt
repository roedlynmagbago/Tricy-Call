package com.example.nasugbutricycleapp.repository
import com.example.nasugbutricycleapp.firebase.FirebaseManager
import com.example.nasugbutricycleapp.model.Booking
import com.example.nasugbutricycleapp.model.AvailableBooking
import com.example.nasugbutricycleapp.model.BookingContact

object BookingRepository {
    fun createBooking(
        booking: Booking,
        onResult: (Boolean, String) -> Unit
    ) {
        val uid = FirebaseManager.auth.currentUser?.uid
        if (uid == null) {
            onResult(false, "Please log in first.")
            return
        }
        if (!booking.passengerPhone.trim()
                .matches(Regex("^09[0-9]{9}$"))) {
            onResult(false, "Invalid passenger phone number.")
            return
        }
        if (booking.pickupLocation.isBlank() ||
            booking.destination.isBlank()) {
            onResult(false, "Please select pickup and destination.")
            return
        }
        if (booking.pickupLatitude !in -90.0..90.0 ||
            booking.pickupLongitude !in -180.0..180.0 ||
            booking.destinationLatitude !in -90.0..90.0 ||
            booking.destinationLongitude !in -180.0..180.0 ||
            (booking.pickupLatitude == 0.0 && booking.pickupLongitude == 0.0) ||
            (booking.destinationLatitude == 0.0 &&
                    booking.destinationLongitude == 0.0)) {
            onResult(false, "Invalid location coordinates.")
            return
        }
        val db = FirebaseManager.db
        val bookingRef = db.collection("bookings").document()
        val contactRef = db.collection("bookingContacts")
            .document(bookingRef.id)
        val availableBooking = AvailableBooking(
            bookingId = bookingRef.id,
            pickupLocation = booking.pickupLocation.trim(),
            pickupLatitude = booking.pickupLatitude,
            pickupLongitude = booking.pickupLongitude,
            destination = booking.destination.trim(),
            destinationLatitude = booking.destinationLatitude,
            destinationLongitude = booking.destinationLongitude,
            driverId = "",
            status = "PENDING"
        )
        val bookingContact = BookingContact(
            bookingId = bookingRef.id,
            passengerId = uid,
            passengerPhone = booking.passengerPhone.trim()
        )
        val batch = db.batch()
        batch.set(bookingRef, availableBooking)
        batch.set(contactRef, bookingContact)
        batch.commit()
            .addOnSuccessListener {
                onResult(true, "Booking created successfully.")
            }
            .addOnFailureListener {
                onResult(false, "Failed to create booking.")
            }
    }
    fun acceptBooking(
        bookingId: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val driverId = FirebaseManager.auth.currentUser?.uid
        if (driverId == null) {
            onResult(false, "Please log in first.")
            return
        }
        val driverRef = FirebaseManager.db
            .collection("users")
            .document(driverId)
        driverRef.get()
            .addOnSuccessListener { driver ->
                if (driver.getString("role") != "DRIVER" ||
                    driver.getString("status") != "ACTIVE" ||
                    driver.getString("driverApplicationStatus") != "APPROVED") {
                    onResult(false, "Only approved drivers can accept bookings.")
                    return@addOnSuccessListener
                }
                val bookingRef = FirebaseManager.db
                    .collection("bookings")
                    .document(bookingId)
                FirebaseManager.db.runTransaction { transaction ->
                    val booking = transaction.get(bookingRef)
                    if (!booking.exists() ||
                        booking.getString("status") != "PENDING" ||
                        booking.getString("driverId") != "") {
                        throw Exception("Booking is no longer available.")
                    }
                    transaction.update(
                        bookingRef,
                        mapOf(
                            "driverId" to driverId,
                            "status" to "ACCEPTED"
                        )
                    )
                }
                    .addOnSuccessListener {
                        onResult(true, "Booking accepted successfully.")
                    }
                    .addOnFailureListener {
                        onResult(false, "Unable to accept booking.")
                    }
            }
            .addOnFailureListener {
                onResult(false, "Unable to verify driver account.")
            }
    }
    fun completeBooking(
        bookingId: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val driverId = FirebaseManager.auth.currentUser?.uid
        if (driverId == null) {
            onResult(false, "Please log in first.")
            return
        }
        val db = FirebaseManager.db
        val bookingRef = db.collection("bookings").document(bookingId)
        db.runTransaction { transaction ->
            val booking = transaction.get(bookingRef)
            if (!booking.exists()) {
                throw Exception("Booking not found.")
            }
            if (booking.getString("driverId") != driverId) {
                throw Exception("You are not the assigned driver.")
            }
            if (booking.getString("status") != "ACCEPTED") {
                throw Exception("Booking must be accepted first.")
            }
            transaction.update(bookingRef, "status", "COMPLETED")
        }
            .addOnSuccessListener {
                onResult(true, "Booking completed successfully.")
            }
            .addOnFailureListener { error ->
                onResult(false, error.message ?: "Unable to complete booking.")
            }
    }

}
