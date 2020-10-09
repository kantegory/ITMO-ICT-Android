package com.example.naivecontacts

import Contact
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class ContactsAdapter(private val contactsNumber: Int, private val contactsList : List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    private var contactHolderNumber : Int = 0

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactName : TextView = itemView.findViewById(R.id.contactName)
        private val phoneNumber : TextView = itemView.findViewById(R.id.contactPhone)

        fun bind(index: Int, list: List<Contact>) {
            contactName.text = list[index].name
            phoneNumber.text = list[index].phoneNumber
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val context : Context = parent.context

        val layoutIdForContactItem : Int = R.layout.contact_item

        val inflater : LayoutInflater = LayoutInflater.from(context)

        val view : View = inflater.inflate(layoutIdForContactItem, parent, false)

        val viewHolder = ContactViewHolder(view)

        contactHolderNumber++

        return viewHolder
    }

    override fun getItemCount(): Int {
        return contactsNumber
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(position, contactsList)
    }
}
