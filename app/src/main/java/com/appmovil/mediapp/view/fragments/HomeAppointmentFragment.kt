package com.appmovil.mediapp.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.appmovil.mediapp.R
import com.appmovil.mediapp.databinding.FragmentHomeAppointmentBinding
import com.appmovil.mediapp.view.adapter.AppointmentAdapter
import com.appmovil.mediapp.viewmodel.AuthViewModel
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAppointmentFragment : Fragment() {
    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: FragmentHomeAppointmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeAppointmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        appointmentViewModel.fetchUserData()

        binding.hearthServiceCard.setOnClickListener {
            navigateToAddAppointment(1)
        }
        binding.brainServiceCard.setOnClickListener {
            navigateToAddAppointment(2)
        }
        binding.toothServiceCard.setOnClickListener {
            navigateToAddAppointment(3)
        }
        binding.logOut.setOnClickListener {
            authViewModel.logOut()
            requireActivity().finish()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(true)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    private fun navigateToAddAppointment(serviceId: Int) {
        val bundle = Bundle().apply {
            putSerializable("serviceId", serviceId)
        }
        findNavController().navigate(R.id.action_homeFragment_to_addAppointmentFragment, bundle)
    }
    private fun observeViewModel() {
        observerListAppointment()
    }

    private fun observerListAppointment(){
        var userRole: String? = null

        appointmentViewModel.userData.observe(viewLifecycleOwner) { userData ->
            userData?.let {
                val userName = it["name"]
                val userLastName = it["lastname"]
                val userFullName = "$userName $userLastName"
                binding.welcomeMsg.text = "Hola, $userFullName"

                userRole = it["role"]

                if (userRole == "doctor") {
                    binding.servicesLayout.visibility = View.GONE
                    appointmentViewModel.getDoctorAppointments()
                } else {
                    binding.servicesLayout.visibility = View.VISIBLE
                    appointmentViewModel.getPatientAppointments()
                }
            }
        }
        appointmentViewModel.appointments.observe(viewLifecycleOwner){ listAppointment ->
            val recycler = binding.recyclerViewAppointments
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = AppointmentAdapter(listAppointment, findNavController(), userRole.toString(), appointmentViewModel)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

}