package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.db.DatabaseHelper
import com.example.app.model.Etudiant
import com.example.myapplication.db.EtudiantAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var fabAdd: FloatingActionButton
    private lateinit var adapter: EtudiantAdapter
    private val etudiantsList = mutableListOf<Etudiant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewEtudiants)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = EtudiantAdapter(etudiantsList, this)
        recyclerView.adapter = adapter

        // Floating Action Button for adding student
        fabAdd = findViewById(R.id.floatingAjouterButton)

        // Activity result launcher
        val addEtudiantLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    loadEtudiants()  // Refresh list when adding a new student
                }
            }

        // Set listener to launch AjouterEtudiantActivity
        fabAdd.setOnClickListener {
            val intent = Intent(this, AjouterEtudiantActivity::class.java)
            addEtudiantLauncher.launch(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadEtudiants()  // Refresh the list when returning to the activity
    }

    private fun loadEtudiants() {
        val dbHelper = DatabaseHelper(this)
        etudiantsList.clear()
        etudiantsList.addAll(dbHelper.getAllEtudiants())  // Fetch the updated list from the database
        adapter.notifyDataSetChanged()
    }
}
