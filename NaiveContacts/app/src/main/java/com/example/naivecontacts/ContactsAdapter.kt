package com.example.naivecontacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class ContactsAdapter(private val contactsNumber: Int) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    private var contactHolderNumber : Int = 0

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactName : TextView = itemView.findViewById(R.id.contactName)
        val contactHolder : TextView = itemView.findViewById(R.id.contactHolder)

        fun bind(index: Number) {
            contactName.text = index.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val context : Context = parent.context

        val layoutIdForContactItem : Int = R.layout.contact_item

        val inflater : LayoutInflater = LayoutInflater.from(context)

        val view : View = inflater.inflate(layoutIdForContactItem, parent, false)

        val viewHolder = ContactViewHolder(view)

        viewHolder.contactHolder.text = "Index: $contactHolderNumber"
        contactHolderNumber++

        return viewHolder
    }

    override fun getItemCount(): Int {
        return contactsNumber
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(position)
    }
}
