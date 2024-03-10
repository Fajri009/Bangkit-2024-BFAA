package com.fajri.bangkit_2024_bfaa.Navigation.AppBar

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.fajri.bangkit_2024_bfaa.R
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with (binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                // menambahkan aksi ketika input di-submit
                .setOnEditorActionListener { textView, actionId, event ->
                    // error val cannot be reassigned
//                    searchBar.text = searchView.text
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    Toast.makeText(this@MenuActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }

            // menambahkan menu di searchBar
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                // Handle menuItem click.
                when (menuItem.itemId) {
                    R.id.menu1 -> {
                        startActivity(Intent(this@MenuActivity, MainAppBar::class.java))
                    }
                    R.id.menu2 -> {
                        startActivity(Intent(this@MenuActivity, MenuActivity::class.java))
                    }
                }
                true
            }
        }
    }
}