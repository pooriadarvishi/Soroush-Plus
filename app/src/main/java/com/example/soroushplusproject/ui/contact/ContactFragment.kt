package com.example.soroushplusproject.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soroushplusproject.R
import com.example.soroushplusproject.databinding.FragmentContactBinding
import com.example.soroushplusproject.ui.contact.adapter.ContactAdapter
import com.example.soroushplusproject.util.isGrantedPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactFragment : Fragment() {

    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var binding: FragmentContactBinding
    private lateinit var adapter: ContactAdapter


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

    override fun onResume() {
        super.onResume()
        checkPermission()
    }


    private fun checkPermission() {
        if (!requireContext().isGrantedPermission()) navigateToPermission()
    }

    private fun navigateToPermission() {
        findNavController().navigate(R.id.action_contactFragment_to_permissionFragment)
    }


    private fun setRecyclerView() {
        adapter = ContactAdapter()
        binding.apply {
            rvContacts.adapter = adapter
            rvContacts.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onObserve() {
        contactViewModel.contacts.observe(viewLifecycleOwner) { contacts ->
            adapter.submitList(contacts)
        }
    }

    private fun setUi() {
        setRecyclerView()
        onObserve()
    }
}