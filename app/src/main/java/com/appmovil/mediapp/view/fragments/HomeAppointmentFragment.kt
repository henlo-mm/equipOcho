package com.appmovil.mediapp.view.fragments

import android.os.Bundle
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
import com.appmovil.mediapp.view.adapters.AppointmentAdapter
import com.appmovil.mediapp.viewmodel.MedicalAppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeAppointmentFragment : Fragment() {
    private val appointmentViewModel: MedicalAppointmentViewModel by viewModels()
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

        binding.hearthServiceCard.setOnClickListener {
            navigateToAddAppointment(1)
        }
        binding.brainServiceCard.setOnClickListener {
            navigateToAddAppointment(2)
        }
        binding.toothServiceCard.setOnClickListener {
            navigateToAddAppointment(3)
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
        appointmentViewModel.getPatientAppointments()
        appointmentViewModel.appointments.observe(viewLifecycleOwner){ listAppointment ->
            val recycler = binding.recyclerViewAppointments
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = AppointmentAdapter(listAppointment, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

}