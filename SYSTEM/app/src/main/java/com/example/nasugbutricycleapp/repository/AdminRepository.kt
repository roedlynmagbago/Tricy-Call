package com.example.nasugbutricycleapp.repository
import com.example.nasugbutricycleapp.firebase.FirebaseManager

object AdminRepository {

    fun checkAdmin(
        onResult: (Boolean) -> Unit
    ) {
        val uid = FirebaseManager.auth.currentUser?.uid

        if (uid == null) {
            onResult(false)
            return
        }

        FirebaseManager.db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                val role = document.getString("role")

                onResult(
                    role == "ADMIN" || role == "SUPER_ADMIN"
                )
            }
            .addOnFailureListener {
                onResult(false)
            }
    }
}
