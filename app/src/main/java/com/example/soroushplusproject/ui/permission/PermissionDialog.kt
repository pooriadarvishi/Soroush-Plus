package com.example.soroushplusproject.ui.permission

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.soroushplusproject.databinding.DialogPermissionBinding

class PermissionDialog : DialogFragment() {
    private lateinit var binding: DialogPermissionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogPermissionBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUi()
    }

    private fun setIntentSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", requireActivity().packageName, null)
        intent.data = uri
        startActivity(intent)
    }

    private fun onSetClickButton() {
        binding.btnContinue.setOnClickListener {
            dismiss()
            setIntentSetting()
        }
        binding.btnNeverMind.setOnClickListener {
            dismiss()
        }
    }

    private fun setUi() {
        onSetClickButton()
    }
}