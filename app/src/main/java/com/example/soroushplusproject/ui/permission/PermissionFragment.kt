package com.example.soroushplusproject.ui.permission

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.soroushplusproject.R
import com.example.soroushplusproject.databinding.FragmentPermissionBinding

class PermissionFragment : Fragment() {
    companion object {
        const val TAG = "Permission"
    }


    private lateinit var binding: FragmentPermissionBinding
    private val permissionViewModel: PermissionViewModel by viewModels()
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            permissionViewModel.permissionGranted()
        } else {
            permissionViewModel.permissionDenied()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPermissionBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGetContact.setOnClickListener {
            if (permissionViewModel.showPermissionState()) {

                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun observePermission() {
        permissionViewModel.permissionState.observe(viewLifecycleOwner) { isGranted ->
            // TODO navigate
            Log.e(TAG, "observePermission: navigate")
        }
    }

    private fun showPermissionDeniedDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setMessage(getString(R.string.access_of_setting)).setCancelable(false)
            .setPositiveButton(getString(R.string.setting)) { _, _ ->
                setIntentSetting()
            }.setNegativeButton(getString(R.string.never_mind)) { dialog, _ ->
                dialog.cancel()
            }
        val alert = alertDialogBuilder.create()
        alert.show()
    }

    private fun setIntentSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}