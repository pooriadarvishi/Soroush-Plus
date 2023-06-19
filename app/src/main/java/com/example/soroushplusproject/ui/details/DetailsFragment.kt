package com.example.soroushplusproject.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soroushplusproject.databinding.FragmentDetailsBinding
import com.example.soroushplusproject.domain.interact_result.InteractResultState
import com.example.soroushplusproject.ui.models.ContactDetails
import com.example.soroushplusproject.util.loadImage
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {
    companion object{
        const val CONTACT_ID = "contact_id"
    }


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
            when (contact) {
                InteractResultState.Empty -> {}
                InteractResultState.Error -> {}
                InteractResultState.Loading -> bindLoading()
                is InteractResultState.Success -> bindSuccess(contact.result)
            }
        }
    }


    private fun bindLoading() {
        binding.svDetails.isInvisible = true
        binding.progressBar.isInvisible = false
    }

    private fun bindSuccess(contactDetails: ContactDetails) {
        onBind(contactDetails)
        binding.svDetails.isInvisible = false
        binding.progressBar.isInvisible = true
    }
}