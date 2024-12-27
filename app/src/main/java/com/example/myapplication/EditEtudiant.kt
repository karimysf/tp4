package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.db.DatabaseHelper


class EditEtudiant : AppCompatActivity() {

    private lateinit var editNom: EditText
    private lateinit var editTelephone: EditText
    private lateinit var editEmail: EditText
    private lateinit var btnSauvegarder: Button
    private lateinit var btnAnnuler: Button
    private var etudiantId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_etudiant)

        editNom = findViewById(R.id.editNom)
        editTelephone = findViewById(R.id.editTelephone)
        editEmail = findViewById(R.id.editEmail)
        btnSauvegarder = findViewById(R.id.btnSauvegarder)
        btnAnnuler = findViewById(R.id.btnAnnuler)

        etudiantId = intent.getIntExtra("etudiantId", -1)

        if (etudiantId != -1) {
            val dbHelper = DatabaseHelper(this)
            val etudiant = dbHelper.getEtudiant(etudiantId)
            etudiant?.let {
                editNom.setText(it.nom)
                editTelephone.setText(it.telephone)
                editEmail.setText(it.email)
            }
        }

        btnSauvegarder.setOnClickListener {
            println(etudiantId)
            val nom = editNom.text.toString().trim()
            val telephone = editTelephone.text.toString().trim()
            val email = editEmail.text.toString().trim()

            if (nom.isEmpty()) {
                Toast.makeText(this, "Le nom ne peut pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (telephone.isEmpty()) {
                Toast.makeText(this, "Le numéro de téléphone ne peut pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "L'email ne peut pas être vide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Email invalide", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dbHelper = DatabaseHelper(this)
            val result = dbHelper.modifierEtudiant(etudiantId, nom, telephone, email)

            if (result > 0) {
                val resultIntent = Intent().apply {
                    putExtra("updatedNom", nom)
                    putExtra("updatedTelephone", telephone)
                    putExtra("updatedEmail", email)
                }

                // Set the result to indicate success and pass the updated data
                setResult(RESULT_OK, resultIntent)
                Toast.makeText(this, "Étudiant modifié avec succès", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Erreur lors de la modification de l'étudiant", Toast.LENGTH_SHORT).show()
            }
        }

        btnAnnuler.setOnClickListener {
            finish()
        }

    }
}
