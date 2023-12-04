package com.example.startree.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.PreferenceUtil
import com.example.startree.InitializeDatabase
import com.example.startree.R
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var initializeDatabase: InitializeDatabase

    lateinit var prefs : PreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        prefs = PreferenceUtil(applicationContext)

        requestPermission {
            databaseCheck {
                todo()
            }
        }
    }

    private fun databaseCheck(done : () -> Unit) {
        val initialized = prefs.getSharedPrefs("initialized", "false")
        if (initialized == "false") {
            CoroutineScope(Dispatchers.IO).launch {
                initializeDatabase.parseAndInsertData()
            }
            prefs.setSharedPrefs("initialized", "true")
        }
        done()
    }

    private fun todo() {
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)
        finish()
    }

    private fun requestPermission(logic : () -> Unit) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            TedPermission.create()
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
                        logic()
                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                        Toast.makeText(this@SplashActivity, "권한 필요", Toast.LENGTH_SHORT).show()
                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_MEDIA_IMAGES)//, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO)
                .check()
        }
        else {
            TedPermission.create()
                .setPermissionListener(object : PermissionListener {
                    override fun onPermissionGranted() {
                        logic()
                    }

                    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                        Toast.makeText(this@SplashActivity, "권한 필요", Toast.LENGTH_SHORT).show()
                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)//, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO)
                .check()
        }
    }
}