package com.example.naivecontacts

import Contact
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fetchAllContacts
import kotlinx.android.synthetic.main.contact_item.view.*


class MainActivity : AppCompatActivity() {

    private var REQUEST_CODE_PERMISSION_READ_CONTACTS : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissionStatus =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)

        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            val contactsList: RecyclerView = findViewById(R.id.recyclerViewContacts)
            val fetchedContacts : List<Contact> = fetchAllContacts()
            val contactsAdapter: ContactsAdapter = ContactsAdapter(fetchedContacts.size, fetchedContacts)

            val layoutManager = LinearLayoutManager(this)
            contactsList.layoutManager = layoutManager
            contactsList.adapter = contactsAdapter
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_PERMISSION_READ_CONTACTS
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION_READ_CONTACTS -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                    val contactsList: RecyclerView = findViewById(R.id.recyclerViewContacts)
                    val fetchedContacts : List<Contact> = fetchAllContacts()
                    val contactsAdapter: ContactsAdapter = ContactsAdapter(fetchedContacts.size, fetchedContacts)

                    val layoutManager = LinearLayoutManager(this)
                    contactsList.layoutManager = layoutManager
                    contactsList.adapter = contactsAdapter

                    Toast.makeText(applicationContext,"Найдено " + fetchedContacts.size + " контактов", Toast.LENGTH_SHORT).show()
                } else {
                    // permission denied
                    Toast.makeText(applicationContext,"К сожалению, Вы не дали доступа к контактам", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }

    fun makeCall(view: View) {
        val phoneNumber : Uri = Uri.parse("tel:" + view.contactPhone.text.toString())
        val callIntent : Intent = Intent(Intent.ACTION_DIAL, phoneNumber)
        startActivity(callIntent)
    }
}