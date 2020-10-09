package com.example.naivecontacts

import Contact
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fetchAllContacts

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val contactsList: RecyclerView = findViewById(R.id.recyclerViewContacts)
        val fetchedContacts : List<Contact> = fetchAllContacts()
        val contactsAdapter: ContactsAdapter = ContactsAdapter(fetchedContacts.size, fetchedContacts)

        val layoutManager = LinearLayoutManager(this)
        contactsList.layoutManager = layoutManager
        contactsList.adapter = contactsAdapter
    }
}