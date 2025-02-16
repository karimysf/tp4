package com.example.myapplication

import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app.model.Etudiant
//import com.example.app.db.DatabaseHelper
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.ImageView
import android.widget.TextView

class AjouterEtudiantActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var formTitle: TextView
    private lateinit var nameInput: TextInputLayout
    private lateinit var dobInput: TextInputLayout
    private lateinit var phoneInput: TextInputLayout
    private lateinit var emailInput: TextInputLayout
    private lateinit var departmentSpinner: Spinner
    private lateinit var hommeRadio: RadioButton
    private lateinit var femmeRadio: RadioButton
    private lateinit var accepterCheckBox: CheckBox
    private lateinit var annulerButton: Button
    private lateinit var sauverButton: Button

//    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajouter_etudiant)

        // Initialize views
        imageView = findViewById(R.id.emi)
        formTitle = findViewById(R.id.formTitle)
        nameInput = findViewById(R.id.nameInput)
        dobInput = findViewById(R.id.dobInput)
        phoneInput = findViewById(R.id.phoneInput)
        emailInput = findViewById(R.id.emailInput)
        departmentSpinner = findViewById(R.id.departmentSpinner)
        hommeRadio = findViewById(R.id.homme)
        femmeRadio = findViewById(R.id.femme)
        accepterCheckBox = findViewById(R.id.accepter)
        annulerButton = findViewById(R.id.annuler)
        sauverButton = findViewById(R.id.sauvgarder)

        // Initialize DatabaseHelper
//        dbHelper = DatabaseHelper(this)

        // Set listeners
        annulerButton.setOnClickListener { clearFields() }
        sauverButton.setOnClickListener { saveStudent() }
    }

    private fun clearFields() {
        nameInput.editText?.text?.clear()
        dobInput.editText?.text?.clear()
        phoneInput.editText?.text?.clear()
        emailInput.editText?.text?.clear()
        departmentSpinner.setSelection(0)
        accepterCheckBox.isChecked = false
        hommeRadio.isChecked = false
        femmeRadio.isChecked = false
    }

    private fun saveStudent() {
        val name = nameInput.editText?.text.toString()
        val dob = dobInput.editText?.text.toString()
        val phone = phoneInput.editText?.text.toString()
        val email = emailInput.editText?.text.toString()
        val department = departmentSpinner.selectedItem.toString()
        val sexe = if (hommeRadio.isChecked) "Homme" else if (femmeRadio.isChecked) "Femme" else ""
        val accepter = accepterCheckBox.isChecked

        // Validation
        if (name.isEmpty() || dob.isEmpty() || phone.isEmpty() || email.isEmpty() || sexe.isEmpty() || !accepter) {
            Toast.makeText(this, "Veuillez remplir tous les champs et accepter les conditions.", Toast.LENGTH_SHORT).show()
            return
        }

        // Create Etudiant object
        val nouvelEtudiant = Etudiant(
            id = 2001,  // Use a custom ID if needed
            nom = name,
            dateNaissance = dob,
            telephone = phone,
            email = email,
            niveau = "N/A",  // Default value
            sexe = sexe,
            departement = department
        )

        // Save to Firebase Firestore
        val db = FirebaseFirestore.getInstance()
        db.collection("students")
            .document(nouvelEtudiant.id.toString())  // Use the custom int ID as the document ID (converted to String)
            .set(nouvelEtudiant)
            .addOnSuccessListener {
                Toast.makeText(this, "Étudiant ajouté avec ID personnalisé: ${nouvelEtudiant.id}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erreur lors de l'ajout: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        // Save to Local Database (DatabaseHelper)
//        val dbHelper = DatabaseHelper(this)
//        val db2 = dbHelper.writableDatabase
//        dbHelper.ajouterEtudiant(db2, nouvelEtudiant)

        // Clear fields after adding
        clearFields()
    }
}
