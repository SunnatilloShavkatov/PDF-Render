package uz.quantic.bookpdf.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import shs.itexperts.bookpdf.R


class ContentActivity : AppCompatActivity() {

    private lateinit var button: CardView
    private lateinit var button1: CardView
    private lateinit var button2: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)

        if (checkPermission()) {
            Toast.makeText(this,"Ruxsat berilgan", Toast.LENGTH_LONG).show()
        } else {
            requestPermission() // Code for permission
        }

        button = findViewById(R.id.main)
        button1 = findViewById(R.id.main1)
        button2 = findViewById(R.id.main2)

        button.setOnClickListener {
            val intent = Intent(this@ContentActivity, MainActivity::class.java)
            startActivity(intent)
        }
        button1.setOnClickListener {
            val intent = Intent(
                this@ContentActivity,
                ExternalStorageActivity::class.java
            )
            startActivity(intent)
        }
        button2.setOnClickListener {
            val intent = Intent(
                this@ContentActivity,
                WritePdfActivity::class.java
            )
            startActivity(intent)
        }

    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this@ContentActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this@ContentActivity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(
                this@ContentActivity,
                "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            ActivityCompat.requestPermissions(
                this@ContentActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .")
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .")
                }
        }
    }
}
