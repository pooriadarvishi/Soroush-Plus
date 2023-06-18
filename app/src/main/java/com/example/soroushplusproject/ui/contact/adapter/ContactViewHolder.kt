package com.example.soroushplusproject.ui.contact.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.soroushplusproject.databinding.ContactItemReBinding
import com.example.soroushplusproject.ui.models.ContactItem
import com.example.soroushplusproject.util.loadImage

class ContactViewHolder(private val binding: ContactItemReBinding, onClick: onClickContact) :
    RecyclerView.ViewHolder(binding.root) {

    lateinit var contact: ContactItem

    init {
        binding.root.setOnClickListener { contact.id?.let { onClick(it) } }
    }

    fun bind(contactItem: ContactItem) {
        contact = contactItem
        binding.apply {
            tvContactName.text = contact.name
            contactItem.image?.let { igContactProfile.loadImage(it) }
        }
    }
}