package com.example.soroushplusproject.ui.contact.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.soroushplusproject.databinding.ContactItemReBinding
import com.example.soroushplusproject.ui.models.ContactItem
import com.example.soroushplusproject.util.onClickContact

class ContactAdapter(val onCLick: onClickContact) :
    ListAdapter<ContactItem, ContactViewHolder>(object : DiffUtil.ItemCallback<ContactItem>() {
        override fun areItemsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ContactItem, newItem: ContactItem): Boolean =
            oldItem == newItem

    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder =
        ContactViewHolder(
            ContactItemReBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onCLick
        )

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}