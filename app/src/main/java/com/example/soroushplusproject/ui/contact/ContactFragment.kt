package com.example.soroushplusproject.ui.contact

import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soroushplusproject.R
import com.example.soroushplusproject.data.model.ContactItem
import com.example.soroushplusproject.databinding.FragmentContactBinding
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.ui.base.BaseFragment
import com.example.soroushplusproject.ui.contact.adapter.ContactAdapter
import com.example.soroushplusproject.util.onShowSnackBarWithAction
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactFragment : BaseFragment<FragmentContactBinding>(R.layout.fragment_contact) {

    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var adapter: ContactAdapter
    private lateinit var navController: NavController

    private fun setRecyclerView() {
        navController = findNavController()
        adapter = ContactAdapter { navigateToDetails(it) }
        binding.apply {
            rvContacts.adapter = adapter
            rvContacts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun navigateToDetails(contactId: Int) {
        navController.navigate(
            ContactFragmentDirections.actionContactFragmentToDetailsFragment(
                contactId
            )
        )
    }

    private fun onObserve() {
        contactViewModel.dataState.observe(viewLifecycleOwner) { contacts ->
            when (contacts) {
                InteractResultState.Empty -> bindEmpty()
                InteractResultState.Error -> bindError()
                InteractResultState.Loading -> bindLoading()
                is InteractResultState.Success -> bindSuccess(contacts.result)
            }

        }
    }

    override fun setUi() {
        setRecyclerView()
        onObserve()
        setSearchView()
    }


    private fun bindEmpty() {
        binding.progressBar.isInvisible = true
        binding.tvNotFind.isInvisible = false
        binding.rvContacts.isInvisible = true
    }

    private fun bindLoading() {
        binding.progressBar.isInvisible = false
        binding.tvNotFind.isInvisible = true
        binding.rvContacts.isInvisible = true
    }

    private fun bindSuccess(contacts: List<ContactItem>) {
        adapter.submitList(contacts)
        binding.progressBar.isInvisible = true
        binding.tvNotFind.isInvisible = true
        binding.rvContacts.isInvisible = false
    }

    private fun bindError() {
        onShowSnackBarWithAction(
            binding.root, getString(R.string.receiving_problem), getString(R.string.retry)
        ) { contactViewModel.startShowAllContact() }
    }


    private fun setSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    contactViewModel.searchContact(newText)
                } else {
                    contactViewModel.startShowAllContact()
                }
                return true
            }
        })
    }
}