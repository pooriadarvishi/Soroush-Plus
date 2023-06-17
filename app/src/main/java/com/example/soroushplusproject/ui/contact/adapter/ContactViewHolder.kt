package com.example.soroushplusproject.ui.contact.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.soroushplusproject.databinding.ContactItemReBinding
import com.example.soroushplusproject.ui.models.ContactItem

class ContactViewHolder(private val binding: ContactItemReBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(contactItem: ContactItem) {
        binding.apply {
            tvContactName.text = contactItem.name
        }
    }
}