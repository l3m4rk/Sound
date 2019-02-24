package me.l3m4rk.test.presentation

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import me.l3m4rk.test.R

class MainActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
