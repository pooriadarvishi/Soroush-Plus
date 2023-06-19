package com.example.soroushplusproject.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soroushplusproject.databinding.FragmentContactBinding
import com.example.soroushplusproject.domain.base.InteractResultState
import com.example.soroushplusproject.ui.contact.adapter.ContactAdapter
import com.example.soroushplusproject.ui.models.ContactItem
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactFragment : Fragment() {

    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var binding: FragmentContactBinding
    private lateinit var adapter: ContactAdapter
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }


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
        contactViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            when (contacts) {
                InteractResultState.Empty -> bindEmpty()
                InteractResultState.Error -> {}
                InteractResultState.Loading -> bindLoading()
                is InteractResultState.Success -> bindSuccess(contacts.result)
            }

        }
    }

    private fun setUi() {
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