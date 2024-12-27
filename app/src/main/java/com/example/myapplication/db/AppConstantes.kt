package com.example.myapplication.db


object DatabaseConstants {
    const val DATABASE_NAME = "emi.db"
    const val DATABASE_VERSION = 1

    object Etudiant {
        const val TABLE_NAME = "Etudiant"
        const val COLUMN_ID = "id"
        const val COLUMN_NOM = "nom"
        const val COLUMN_DATE_NAISSANCE = "date_naissance"
        const val COLUMN_TELEPHONE = "telephone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_NIVEAU = "niveau"
        const val COLUMN_SEXE = "sexe"
        const val COLUMN_DEPARTEMENT = "departement"
    }
}

