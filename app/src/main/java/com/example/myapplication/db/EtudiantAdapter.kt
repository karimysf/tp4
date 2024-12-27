package com.example.myapplication.db

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.app.db.DatabaseHelper
import com.example.app.model.Etudiant
import com.example.myapplication.EditEtudiant
import com.example.myapplication.R

class EtudiantAdapter(private val etudiants: MutableList<Etudiant>, private val context: Context) :
    RecyclerView.Adapter<EtudiantAdapter.EtudiantViewHolder>() {

    inner class EtudiantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomTextView: TextView = itemView.findViewById(R.id.etudiantNom)
        val telephoneTextView: TextView = itemView.findViewById(R.id.etudiantTelephone)
        val callButton: ImageButton = itemView.findViewById(R.id.btn_call)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete)
        val editButton: ImageButton = itemView.findViewById(R.id.btn_edit)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EtudiantViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_etudiant, parent, false)
        return EtudiantViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EtudiantViewHolder, position: Int) {
        val etudiant = etudiants[position]
        holder.nomTextView.text = etudiant.nom
        holder.telephoneTextView.text = etudiant.telephone

        holder.callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${etudiant.telephone}")
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            val dbHelper = DatabaseHelper(context)
            val db = dbHelper.writableDatabase

                val result = etudiant.id?.let { it1 -> dbHelper.supprimerEtudiant(db, it1) }


            if (result != null) {
                if (result > 0) {
                    etudiants.removeAt(position)
                    notifyItemRemoved(position)
                    Toast.makeText(context, "Étudiant supprimé", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show()
                }
            }
        }
        holder.editButton.setOnClickListener {
            val intent = Intent(context, EditEtudiant::class.java)
            intent.putExtra("etudiantId", etudiant.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = etudiants.size
}
