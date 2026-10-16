package com.example.nasugbutricycleapp.repository
import com.example.nasugbutricycleapp.firebase.FirebaseManager
object FirestoreUserRepository {

    fun saveUser(
        userId: String,
        name: String,
        email: String,
        phoneNumber: String,
        applyAsDriver: Boolean,
        onResult: (Boolean) -> Unit
    ) {
        val user = hashMapOf(
            "userId" to userId,
            "name" to name,
            "email" to email,
            "phoneNumber" to phoneNumber.trim(),
            "role" to "PASSENGER",
            "status" to "ACTIVE",
            "driverApplicationStatus" to
                    if (applyAsDriver) "PENDING" else "NONE"
        )

        FirebaseManager.db.collection("users")
            .document(userId)
            .set(user)
            .addOnSuccessListener {
                onResult(true)
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}
