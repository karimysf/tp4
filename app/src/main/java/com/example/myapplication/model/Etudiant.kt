package com.example.app.model

import com.google.firebase.firestore.DocumentId
import java.io.Serializable

data class Etudiant(
    @DocumentId
    var id: Int?=null,
    val nom: String,
    val dateNaissance: String,
    val telephone: String,
    val email: String,
    val niveau: String,
    val sexe: String,
    val departement: String
) :Serializable
