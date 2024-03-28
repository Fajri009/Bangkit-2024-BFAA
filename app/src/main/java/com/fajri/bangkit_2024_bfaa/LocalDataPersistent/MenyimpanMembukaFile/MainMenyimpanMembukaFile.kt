package com.fajri.bangkit_2024_bfaa.LocalDataPersistent.MenyimpanMembukaFile

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fajri.bangkit_2024_bfaa.R
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMainMenyimpanMembukaFileBinding

class MainMenyimpanMembukaFile : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainMenyimpanMembukaFileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainMenyimpanMembukaFileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNew.setOnClickListener(this)
        binding.btnOpen.setOnClickListener(this)
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_new -> newFile()
            R.id.btn_open -> showList()
            R.id.btn_save -> saveFile()
        }
    }

    // membuat berkas baru
    private fun newFile() {
        binding.edtTitle.setText("")
        binding.edtFile.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    // membuka dan menampilkan berkas
    private fun showList() {
        val items = fileList().filter { fileName ->
            fileName != "profileInstalled"
        }.toTypedArray()
        val builder = AlertDialog.Builder(this@MainMenyimpanMembukaFile)
        builder.setTitle("Pilih file yang diinginkan")
        builder.setItems(items) { dialog, item ->
            loadData(items[item].toString())
        }
        val alert = builder.create()
        alert.show()
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this@MainMenyimpanMembukaFile, title)
        binding.edtTitle.setText(fileModel.filename)
        binding.edtFile.setText(fileModel.data)
        Toast.makeText(this@MainMenyimpanMembukaFile, "Loading ${fileModel.filename} data", Toast.LENGTH_SHORT).show()
    }

    // menyimpan berkas
    private fun saveFile() {
        when {
            binding.edtTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            binding.edtFile.text.toString().isEmpty() -> Toast.makeText(this, "Kontent harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.edtTitle.text.toString()
                val text = binding.edtFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this@MainMenyimpanMembukaFile)
                Toast.makeText(this@MainMenyimpanMembukaFile, "Saving ${fileModel.filename} file", Toast.LENGTH_SHORT).show()
            }
        }
    }
}