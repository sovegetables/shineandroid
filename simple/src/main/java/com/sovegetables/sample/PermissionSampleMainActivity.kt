package com.sovegetables.sample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sovegetables.permission.OnPermissionResultListener
import com.sovegetables.permission.PermissionResult
import com.sovegetables.permission.Permissions
import kotlinx.android.synthetic.main.activity_permission_sample.*


class PermissionSampleMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_sample)
        btn_sd_card.setOnClickListener {
            Permissions.request(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), object :
                OnPermissionResultListener {

                override fun allGranted(grantedPermissions: ArrayList<PermissionResult>) {
                    Toast.makeText(this@PermissionSampleMainActivity,"allGranted", Toast.LENGTH_SHORT).show()
                }

                override fun denied(
                    grantedPermissions: ArrayList<PermissionResult>,
                    deniedPermissions: ArrayList<PermissionResult>
                ) {
                    Toast.makeText(this@PermissionSampleMainActivity,"denied", Toast.LENGTH_SHORT).show()
                }

            })
        }
        btn_op.setOnClickListener {
            startActivity(Intent(this, PermissionSampleActivity::class.java))
        }
    }
}
