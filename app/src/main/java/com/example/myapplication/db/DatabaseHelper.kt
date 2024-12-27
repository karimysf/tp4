package com.example.app.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.app.model.Etudiant
import com.example.myapplication.db.DatabaseConstants

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DatabaseConstants.DATABASE_NAME,
    null,
    DatabaseConstants.DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery ="""
            CREATE TABLE ${DatabaseConstants.Etudiant.TABLE_NAME} (
                ${DatabaseConstants.Etudiant.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${DatabaseConstants.Etudiant.COLUMN_NOM} TEXT,
                ${DatabaseConstants.Etudiant.COLUMN_DATE_NAISSANCE} TEXT,
                ${DatabaseConstants.Etudiant.COLUMN_TELEPHONE} TEXT,
                ${DatabaseConstants.Etudiant.COLUMN_EMAIL} TEXT,
                ${DatabaseConstants.Etudiant.COLUMN_NIVEAU} TEXT,
                ${DatabaseConstants.Etudiant.COLUMN_SEXE} TEXT,
                ${DatabaseConstants.Etudiant.COLUMN_DEPARTEMENT} TEXT
            )
        """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseConstants.Etudiant.TABLE_NAME}")
        onCreate(db)
    }

    fun ajouterEtudiant(db: SQLiteDatabase, etudiant: Etudiant): Long {
        val values = ContentValues()
        values.put(DatabaseConstants.Etudiant.COLUMN_NOM, etudiant.nom)
        values.put(DatabaseConstants.Etudiant.COLUMN_DATE_NAISSANCE, etudiant.dateNaissance)
        values.put(DatabaseConstants.Etudiant.COLUMN_TELEPHONE, etudiant.telephone)
        values.put(DatabaseConstants.Etudiant.COLUMN_EMAIL, etudiant.email)
        values.put(DatabaseConstants.Etudiant.COLUMN_NIVEAU, etudiant.niveau)
        values.put(DatabaseConstants.Etudiant.COLUMN_SEXE, etudiant.sexe)
        values.put(DatabaseConstants.Etudiant.COLUMN_DEPARTEMENT, etudiant.departement)

        return db.insert(DatabaseConstants.Etudiant.TABLE_NAME, null, values)
    }
    fun getAllEtudiants(): MutableList<Etudiant> {
        val db = this.readableDatabase
        val etudiantsList = mutableListOf<Etudiant>()

        val selectAll = "SELECT * FROM ${DatabaseConstants.Etudiant.TABLE_NAME}"
        val cursor = db.rawQuery(selectAll, null)

        if (cursor.moveToFirst()) {
            do {
                val etudiant = Etudiant(
                    id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_ID)),
                    nom = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_NOM)),
                    dateNaissance = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_DATE_NAISSANCE)),
                    telephone = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_TELEPHONE)),
                    email = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_EMAIL)),
                    niveau = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_NIVEAU)),
                    sexe = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_SEXE)),
                    departement = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_DEPARTEMENT))
                )
                etudiantsList.add(etudiant)
            } while (cursor.moveToNext())
        }

        cursor.close()
        return etudiantsList
    }
    fun supprimerEtudiant(db: SQLiteDatabase, etudiantId: Int): Int {
        val whereClause = "${DatabaseConstants.Etudiant.COLUMN_ID} = ?"
        val whereArgs = arrayOf(etudiantId.toString())
        return db.delete(DatabaseConstants.Etudiant.TABLE_NAME, whereClause, whereArgs)
    }

    fun getEtudiant(etudiantId: Int): Etudiant? {
        val db = this.readableDatabase
        val query = "SELECT * FROM ${DatabaseConstants.Etudiant.TABLE_NAME} WHERE ${DatabaseConstants.Etudiant.COLUMN_ID} = ?"
        val cursor = db.rawQuery(query, arrayOf(etudiantId.toString()))

        return if (cursor.moveToFirst()) {
            val etudiant = Etudiant(
                id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_ID)),
                nom = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_NOM)),
                dateNaissance = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_DATE_NAISSANCE)),
                telephone = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_TELEPHONE)),
                email = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_EMAIL)),
                niveau = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_NIVEAU)),
                sexe = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_SEXE)),
                departement = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_DEPARTEMENT))
            )
            cursor.close()
            etudiant
        } else {
            cursor.close()
            null
        }
    }

    fun modifierEtudiant(etudiantId: Int, nom: String, telephone: String, email: String): Int {
        val db = this.writableDatabase

        val existingEtudiant = getEtudiant(etudiantId)
        if (existingEtudiant == null) {
            return 0
        }

        val contentValues = ContentValues().apply {
            put(DatabaseConstants.Etudiant.COLUMN_NOM, nom)
            put(DatabaseConstants.Etudiant.COLUMN_TELEPHONE, telephone)
            put(DatabaseConstants.Etudiant.COLUMN_EMAIL, email)
        }

        val whereClause = "${DatabaseConstants.Etudiant.COLUMN_ID} = ?"
        val whereArgs = arrayOf(etudiantId.toString())


        val rowsAffected = db.update(DatabaseConstants.Etudiant.TABLE_NAME, contentValues, whereClause, whereArgs)



        return rowsAffected
    }
    fun afficherTousLesEtudiantsDansLog() {
        val db = this.readableDatabase
        val query = "SELECT ${DatabaseConstants.Etudiant.COLUMN_ID}, ${DatabaseConstants.Etudiant.COLUMN_NOM} FROM ${DatabaseConstants.Etudiant.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_ID))
                val nom = cursor.getString(cursor.getColumnIndex(DatabaseConstants.Etudiant.COLUMN_NOM))
            } while (cursor.moveToNext())
        }

        cursor.close()
    }



}

