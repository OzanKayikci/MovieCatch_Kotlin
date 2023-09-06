package com.example.moviecatch.di.dao

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.ValueEventListener

import com.google.firebase.ktx.Firebase
import javax.inject.Inject

class FirebaseRepository @Inject constructor() {

    fun pushToFirebase(
        databaseRef: DatabaseReference,
        jsonData: String,
        callback: (Boolean, String) -> Unit
    ) {

        databaseRef.child(Firebase.auth.uid!!).setValue(jsonData)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("Firebase", "Data saved successfully")
                    callback(true, "Successfully Saved")
                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    Log.e("Firebase", "Error: $errorMessage")
                    callback(false, errorMessage)
                }
            }
    }

    fun fetchFromFirebase(databaseRef: DatabaseReference, callback: (Boolean, String?) -> Unit) {
        databaseRef.child(Firebase.auth.uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        try {
                            callback(true, snapshot.getValue(String::class.java))
                        } catch (e: Exception) {
                            Log.e("Gson Parsing Error", e.toString())
                            callback(false, "Error")
                        }
                    } else {
                        callback(false, "There is no data to retrieve")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    callback(false, error.message)
                }
            })
    }
}