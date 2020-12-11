package com.example.naivecontacts

import Contact
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView

class ContactsAdapter(private val contactsNumber: Int, private val contactsList : List<Contact>) : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {
    private var contactHolderNumber : Int = 0

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contactName : TextView = itemView.findViewById(R.id.contactName)
        private val phoneNumber : TextView = itemView.findViewById(R.id.contactPhone)
        private val sendMsgTo : TextView = itemView.findViewById(R.id.sendMsgTo)
        private val contactImg : ImageView = itemView.findViewById(R.id.contactImage)
        private val imgsList : Array<Int> = arrayOf<Int>(R.drawable.beglov0, R.drawable.beglov1,
            R.drawable.beglov2, R.drawable.beglov3, R.drawable.beglov4)

        fun bind(index: Int, list: List<Contact>) {
            val rndImgIndex : Int = (0..4).random()
            contactImg.setImageResource(imgsList[rndImgIndex])
            contactName.text = list[index].name
            phoneNumber.text = list[index].phoneNumber
            sendMsgTo.text = list[index].phoneNumber
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
