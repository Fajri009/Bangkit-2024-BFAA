package com.fajri.bangkit_2024_bfaa.BackgroundTaskScheduler.TackBackStack

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.fajri.bangkit_2024_bfaa.R
import com.fajri.bangkit_2024_bfaa.databinding.ActivityMainTackBackStackBinding

class MainTackBackStack : AppCompatActivity() {
    private lateinit var binding: ActivityMainTackBackStackBinding

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTackBackStackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Untuk Android 13 ke atas perlu menambahkan permission
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        val title = getString(R.string.notification_title)
        val message = getString(R.string.notification_message)

        binding.btnSendNotification.setOnClickListener {
            sendNotification(title, message)
        }

        // untuk mengirim data ke halaman detail
        binding.btnOpenDetail.setOnClickListener {
            val detailIntent = Intent(this@MainTackBackStack, DetailActivity::class.java)
            detailIntent.putExtra(DetailActivity.EXTRA_TITLE, title)
            detailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, message)
            startActivity(detailIntent)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val notifDetailIntent = Intent(this@MainTackBackStack, DetailActivity::class.java)
        notifDetailIntent.putExtra(DetailActivity.EXTRA_TITLE, title)
        notifDetailIntent.putExtra(DetailActivity.EXTRA_MESSAGE, message)

        val pendingIntent = TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(notifDetailIntent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            } else {
                getPendingIntent(NOTIFICATION_ID, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(this, MainTackBackStack.CHANNEL_ID)
            // judul dari notifikasi (wajib ada)
            .setContentTitle(title)
            // ikon ini yang akan muncul pada status bar (wajib ada)
            .setSmallIcon(R.drawable.notification_active)
            // text yang akan muncul di bawah judul notifikasi (wajib ada)
            .setContentText(message)
            // untuk menentukan tingkat kepentingan dari notifikasi yang ditampilkan
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // text ini yang akan muncul di bawah content text
            .setSubText(getString(R.string.notification_subtext))
            // intent ini sebagai action jika notifikasi ditekan
            .setContentIntent(pendingIntent)
            // digunakan untuk menghapus notifikasi setelah ditekan
            .setAutoCancel(true)

        // untuk memunculkan notifkasi pada OS Oreo ke atas
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                MainTackBackStack.CHANNEL_ID,
                MainTackBackStack.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            builder.setChannelId(MainTackBackStack.CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(MainTackBackStack.NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "dicoding channel"
    }
}