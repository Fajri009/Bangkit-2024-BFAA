package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.db.DatabaseContract.NoteColumns.Companion.TABLE_NAME
import com.fajri.bangkit_2024_bfaa.LocalDataPersistent.SQLite.db.DatabaseContract.NoteColumns.Companion._ID
import java.sql.SQLException

// Kelas ini untuk mengakomodasi kebutuhan DML
// Tugas utama dari kelas di atas adalah melakukan proses manipulasi data yang berada di dalam tabel seperti query untuk pembacaan data yang diurutkan secara ascending, penyediaan fungsi pencarian catatan berdasarkan judul, pembaruan catatan, dan penghapusan catatan
class NoteHelper(context: Context) {
    private var dataBaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: NoteHelper? = null
        fun getInstance(context: Context): NoteHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: NoteHelper(context)
            }

    }

    // Metode untuk membuka dan menutup koneksi ke database-nya
    @Throws(SQLException::class)
    fun open() {
        database = dataBaseHelper.writableDatabase
    }

    fun close() {
        dataBaseHelper.close()

        if (database.isOpen)
            database.close()
    }

    // Metode untuk melakukan proses CRUD
    // metode pertama adalah untuk mengambil data
    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    // metode untuk mengambil data dengan id tertentu
    fun queryById(id: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null
        )
    }

    // metode untuk menyimpan data
    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    // metode untuk memperbarui data
    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    // metode untuk menghapus data
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$_ID = '$id", null)
    }

    
}