package com.example.soroushplusproject.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soroushplusproject.databinding.FragmentDetailsBinding
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.util.isGrantedPermission
import com.example.soroushplusproject.util.loadImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val detailsViewModel: DetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onObserve()
    }

    override fun onResume() {
        super.onResume()
        checkPermission()

    }

    private fun onBind(contactDetails: ContactDetails) {
        binding.apply {
            tvPhoneNumber.text = contactDetails.phoneNumber
            tvEmail.text = contactDetails.email
            tvNameContact.text = contactDetails.name
            contactDetails.image?.let { igContactProfile.loadImage(it) }
        }
    }

    private fun onObserve() {
        detailsViewModel.contact.observe(viewLifecycleOwner) { contact ->
            onBind(contact)
        }
    }

    private fun checkPermission() {
        if (!requireContext().isGrantedPermission()) sync()
    }


    private fun sync() {
        detailsViewModel.sync()
    }
}