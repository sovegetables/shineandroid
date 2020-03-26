package com.sovegetables.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PermissionSampleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_sample2)

        supportFragmentManager.beginTransaction()
            .add(R.id.csl_fl, SampleFragment())
            .commitNow()
    }
}
