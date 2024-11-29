package com.perpetua.eazytopup.fragments

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.SharedPreferencesManager
import com.perpetua.eazytopup.adapters.FavoritesAdapter
import com.perpetua.eazytopup.databinding.DialogAddFavoriteBinding
import com.perpetua.eazytopup.databinding.FragmentFavoritesBinding
import com.perpetua.eazytopup.models.Contact


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val contactList = mutableListOf<Contact>()
    private val adapter = FavoritesAdapter(contactList) { position -> onDeleteContact(position) }
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferencesManager = SharedPreferencesManager(context)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Load contacts from SharedPreferences
        contactList.addAll(sharedPreferencesManager.loadContacts())
        adapter.notifyDataSetChanged()

        binding.fabAdd.setOnClickListener {
            showAddContactDialog()
        }
        // Attach the ItemTouchHelper to the RecyclerView
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        return view
    }
    private fun showAddContactDialog() {
        val dialogBinding = DialogAddFavoriteBinding.inflate(LayoutInflater.from(requireContext()))
        val dialogView = dialogBinding.root

        val alertDialog = android.app.AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = dialogBinding.editTextName.text.toString()
                val phoneNumber = dialogBinding.editTextPhoneNumber.text.toString()

                if (name.isNotEmpty() && phoneNumber.isNotEmpty()) {
                    val newContact = Contact(name, phoneNumber)
                    contactList.add(newContact)
                    adapter.notifyItemInserted(contactList.size - 1)

                    // Save the updated contact list to SharedPreferences
                    sharedPreferencesManager.saveContacts(contactList)
                }
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        alertDialog.show()
    }
    // Define an ItemTouchHelper to handle swipe-to-delete
    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(

        0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // Get the position of the swiped item
            val position = viewHolder.adapterPosition
            onDeleteContact(position)
        }
    })


    private fun onDeleteContact(position: Int) {
        if (position >= 0 && position < contactList.size) {
            // Remove the contact from the list
            val deletedContact = contactList.removeAt(position)

            // Notify the adapter of the removal
            adapter.notifyItemRemoved(position)

            // Save the updated contact list to SharedPreferences
            sharedPreferencesManager.saveContacts(contactList)

            // Remove the contact from SharedPreferences
            removeContactFromSharedPreferences(deletedContact)
        }
    }

    private fun removeContactFromSharedPreferences(contact: Contact) {
        val contactsFromPrefs = sharedPreferencesManager.loadContacts().toMutableList()
        contactsFromPrefs.remove(contact)
        sharedPreferencesManager.saveContacts(contactsFromPrefs)
    }


}