package com.example.soroushplusproject.ui.details

import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import com.example.soroushplusproject.R
import com.example.soroushplusproject.data.model.ContactDetails
import com.example.soroushplusproject.databinding.FragmentDetailsBinding
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.ui.base.BaseFragment
import com.example.soroushplusproject.util.asFirst
import com.example.soroushplusproject.util.loadImage
import com.example.soroushplusproject.util.onShowSnackBarWithAction
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(R.layout.fragment_details) {
    companion object {
        const val CONTACT_ID = "contact_id"
    }


    private val detailsViewModel: DetailsViewModel by viewModels()


    private fun onBind(contactDetails: ContactDetails) {
        binding.apply {
            tvPhoneNumber.text = contactDetails.phoneNumber
            tvEmail.text = contactDetails.email
            tvNameContact.text = contactDetails.name
            if (!contactDetails.name.isNullOrEmpty()) tvName.text = contactDetails.name.asFirst()
            contactDetails.image?.let {
                igContactProfile.loadImage(it)
                binding.tvName.isInvisible = true
            }
        }
    }

    private fun onObserve() {
        detailsViewModel.dataState.observe(viewLifecycleOwner) { contact ->
            when (contact) {
                InteractResultState.Empty -> bindEmpty()
                InteractResultState.Error -> bindError()
                InteractResultState.Loading -> bindLoading()
                is InteractResultState.Success -> bindSuccess(contact.result)
            }
        }
    }


    private fun bindLoading() {
        binding.svDetails.isInvisible = true
        binding.progressBar.isInvisible = false
    }

    private fun bindError() {
        onShowSnackBarWithAction(
            binding.root, getString(R.string.receiving_problem), getString(R.string.retry)
        ) { detailsViewModel.getContact() }
    }

    private fun bindEmpty() {
        binding.svDetails.isInvisible = true
        binding.progressBar.isInvisible = false
    }

    private fun bindSuccess(contactDetails: ContactDetails) {
        onBind(contactDetails)
        binding.svDetails.isInvisible = false
        binding.progressBar.isInvisible = true
    }

    override fun setUi() {
        onObserve()
    }
}