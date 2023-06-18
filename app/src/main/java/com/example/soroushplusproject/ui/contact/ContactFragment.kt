package com.example.soroushplusproject.ui.contact

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.soroushplusproject.databinding.FragmentContactBinding
import com.example.soroushplusproject.ui.contact.adapter.ContactAdapter
import com.example.soroushplusproject.ui.permission.showDialog
import com.example.soroushplusproject.util.isGrantedPermission
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactFragment : Fragment() {
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

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

    override fun onResume() {
        super.onResume()
        checkPermission()
    }


    private fun checkPermission() {
        if (!requireContext().isGrantedPermission()) getPermission()
        else {
            contactViewModel.connectToContent()
            contactViewModel.sync()
        }
    }

    private fun getPermission() {
        requireContext().showDialog { permissionLauncher.launch(Manifest.permission.READ_CONTACTS) }
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
            adapter.submitList(contacts)
        }
    }

    private fun setUi() {
        setRecyclerView()
        onObserve()
    }
}