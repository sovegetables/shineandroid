package com.sovegetables.sample

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.common.io.Files
import com.qmuiteam.qmui.widget.dialog.QMUIDialog
import com.sovegetables.permission.DeniedHandler
import com.sovegetables.permission.OnPermissionResultListener
import com.sovegetables.permission.PermissionResult
import com.sovegetables.permission.Permissions
import kotlinx.android.synthetic.main.activity_permission_sample.*
import java.io.File
import java.nio.charset.StandardCharsets


class PermissionSampleMainActivity : AppCompatActivity() {

    private var permissionsMap = hashMapOf<String, String>()

    init {
        permissionsMap[android.Manifest.permission.READ_EXTERNAL_STORAGE] = "存储"
        permissionsMap[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] = "存储"
        permissionsMap[android.Manifest.permission.RECORD_AUDIO] = "录音"
        permissionsMap[android.Manifest.permission.READ_CONTACTS] = "通讯录"
        permissionsMap[android.Manifest.permission.WRITE_CONTACTS] = "通讯录"
        permissionsMap[android.Manifest.permission.CALL_PHONE] = "电话"
        permissionsMap[android.Manifest.permission.CAMERA] = "摄像头"
        permissionsMap[android.Manifest.permission.ACCESS_FINE_LOCATION] = "地理位置"
    }

    fun getAppName(context: Context): String? {
        try {
            val packageManager: PackageManager = context.getPackageManager()
            val packageInfo: PackageInfo = packageManager.getPackageInfo(
                context.getPackageName(), 0
            )
            val labelRes = packageInfo.applicationInfo.labelRes
            return context.getResources().getString(labelRes)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_sample)


        Permissions.setDeniedHandler(object : DeniedHandler{
            override fun onHandler(activity: Activity, results: ArrayList<PermissionResult>) {
                val sb = StringBuilder()
                results.forEach { p ->
                    sb.append(permissionsMap[p.permission])
                    sb.append("、")
                }
                if(sb.isNotEmpty()){
                    sb.deleteCharAt(sb.length - 1)
                }
                QMUIDialog.MessageDialogBuilder(activity)
                    .setTitle("温馨提示")
                    .setMessage("${getAppName(activity)}获取你的${sb.toString()}权限仅用于提供服务，请在设置中开启相关权限。")
                    .addAction("去设置"
                    ) { dialog, _ ->
                        try {
                            dialog.dismiss()
                            val i = Intent()
                            i.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
                            i.data = Uri.fromParts("package", activity.packageName, null)
                            activity.startActivity(i)
                        } catch (ignored: Exception) {
                        }
                    }.show()
            }
        })


        val dirName = "/shineAndroid"
        try {
            val externalCacheDir = File(Environment.getExternalStorageDirectory().absolutePath + dirName)
            Log.d("-------externalCacheDir--------", "onCreate: $externalCacheDir")
            val flag = externalCacheDir.createNewFile()
            Files.newWriter(externalCacheDir, StandardCharsets.UTF_8).write("abcdadfadfad")
            Log.d("flag", "onCreate: $flag")
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                val dir = File(externalCacheDir!!.absolutePath + dirName)
                dir.createNewFile()
                val newWriter = Files.newWriter(dir, StandardCharsets.UTF_8)
                newWriter.write("abcdadfadfad")
                newWriter.flush()
                Log.d("-------dir--------", "onCreate: $dir")
            } catch (e1: Exception) {
                Log.d( "onCreate: ", e1.message)
            }
            try {
                val dir2 = File(cacheDir!!.absolutePath +  dirName)
                dir2.createNewFile()
                val newWriter = Files.newWriter(dir2, StandardCharsets.UTF_8)
                newWriter.write("abcdadfadfad")
                newWriter.flush()
                Log.d("-------dir2--------", "onCreate: $dir2")
            } catch (e2: Exception) {
                Log.d( "onCreate: ", e2.message)
            }
        }
        btn_sd_card.setOnClickListener {
            Permissions.request(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), object :
                OnPermissionResultListener {

                override fun allGranted(grantedPermissions: ArrayList<PermissionResult>) {
                    val cacheDir = File(Environment.getExternalStorageDirectory().absolutePath + dirName)
                    Log.d("-------cacheDir--------", "onCreate: $externalCacheDir")
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
