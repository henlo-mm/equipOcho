package com.appmovil.mediapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.appmovil.mediapp.R
import com.appmovil.mediapp.view.fragments.AddAppointmentFragment
import com.appmovil.mediapp.view.fragments.EditAppointmentFragment
import com.appmovil.mediapp.view.fragments.HomeAppointmentFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val email = intent.getStringExtra("email")

     //   val fragment = HomeAppointmentFragment()
        val fragment = EditAppointmentFragment()
        val bundle = Bundle()
        bundle.putString("email", email)
        fragment.arguments = bundle

        loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}