package com.fajri.bangkit_2024_bfaa.BackgroundTaskScheduler.BroadcastReceiverSMS

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.fajri.bangkit_2024_bfaa.R
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMainBroadcastReceiverSmsBinding

class MainBroadcastReceiverSMS : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val ACTION_DOWNLOAD_STATUS = "download_status"
    }

    private lateinit var binding: ActivityMainBroadcastReceiverSmsBinding
    private lateinit var downloadReceiver: BroadcastReceiver

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBroadcastReceiverSmsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPermission.setOnClickListener(this)
        binding.btnDownload.setOnClickListener(this)

        // untuk menerima broadcast yang dibuat sebelumnya
        downloadReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Toast.makeText(context, "Download Selesai", Toast.LENGTH_SHORT).show()
            }
        }
        val downloadIntentFilter = IntentFilter(ACTION_DOWNLOAD_STATUS)
        // registerReceiver yang berisi BroadcastReceiver yang kita buat dan IntentFilter untuk mendefinisikan Action yang ingin dipantau
        registerReceiver(downloadReceiver, downloadIntentFilter, RECEIVER_EXPORTED)
    }

    var requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Sms receiver permission diterima", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sms receiver permission ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_permission -> requestPermissionLauncher.launch(Manifest.permission.RECEIVE_SMS)
            // untuk membuat sebuah Intent dengan action yang sudah kita buat sendiri dan sebarkan broadcast di metode onClick*()
            R.id.btn_download -> {
                // simulate download process in 3 seconds
                Handler(Looper.getMainLooper()).postDelayed({
                    val notifyFinishIntent = Intent().setAction(ACTION_DOWNLOAD_STATUS)
                    sendBroadcast(notifyFinishIntent)
                }, 3000)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadReceiver)
    }
}