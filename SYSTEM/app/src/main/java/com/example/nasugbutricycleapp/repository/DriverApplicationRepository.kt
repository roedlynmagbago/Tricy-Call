package com.example.nasugbutricycleapp.repository

import com.example.nasugbutricycleapp.firebase.FirebaseManager
import com.google.firebase.firestore.DocumentSnapshot

object DriverApplicationRepository {

    // Get pending driver applications
    fun getPendingDrivers(
        onResult: (List<DocumentSnapshot>, String?) -> Unit
    ) {
        FirebaseManager.db.collection("users")
            .whereEqualTo("driverApplicationStatus", "PENDING")
            .get()
            .addOnSuccessListener { documents ->
                onResult(documents.documents, null)
            }
            .addOnFailureListener { error ->
                onResult(emptyList(), error.message)
            }
    }
    // Approve driver application
    fun approveDriver(
        userId: String,
        onResult: (Boolean, String) -> Unit
    ) {
        val updates = mapOf(
            "role" to "DRIVER",
            "driverApplicationStatus" to "APPROVED"
        )
        FirebaseManager.db.collection("users")
            .document(userId)
            .update(updates)
            .addOnSuccessListener {
                onResult(true, "Driver approved successfully.")
            }
            .addOnFailureListener { error ->
                onResult(false, error.message ?: "Approval failed.")
            }
    }
    // Reject driver application
    fun rejectDriver(
        userId: String,
        onResult: (Boolean, String) -> Unit
    ) {
        FirebaseManager.db.collection("users")
            .document(userId)
            .update("driverApplicationStatus", "REJECTED")
            .addOnSuccessListener {
                onResult(true, "Driver application rejected.")
            }
            .addOnFailureListener { error ->
                onResult(false, error.message ?: "Rejection failed.")
            }
    }
}
