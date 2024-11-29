package com.perpetua.eazytopup

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perpetua.eazytopup.models.Contact

class SharedPreferencesManager (context: Context) {
    private val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val historyListType = object : TypeToken<List<HistoryItem>>() {}.type
    //private val pointsListType = object : TypeToken<List<PointsItem>>() {}.type


    fun saveContacts(contacts: List<Contact>) {
        val contactListJson = gson.toJson(contacts)
        sharedPrefs.edit().putString("contactList", contactListJson).apply()
    }

    fun loadContacts(): List<Contact> {
        val contactListJson = sharedPrefs.getString("contactList", null)
        return if (contactListJson != null) {
            gson.fromJson(contactListJson, object : TypeToken<List<Contact>>() {}.type)
        } else {
            emptyList()
        }
    }

    fun saveHistoryItem(historyItem: HistoryItem) {
        val historyList = getHistoryItems()
        historyList.add(historyItem)
        val historyJson = gson.toJson(historyList)
        sharedPrefs.edit().putString("historyList", historyJson).apply()
    }

    fun getHistoryItems(): MutableList<HistoryItem> {
        val historyJson = sharedPrefs.getString("historyList", null)
        return if (!historyJson.isNullOrBlank()) {
            gson.fromJson(historyJson, historyListType)
        } else {
            mutableListOf()
        }
    }
    fun removeHistoryItem(position: Int) {
        val historyList = getHistoryItems()
        if (position >= 0 && position < historyList.size) {
            historyList.removeAt(position)
            val historyJson = gson.toJson(historyList)
            sharedPrefs.edit().putString("historyList", historyJson).apply()
        }
    }
    fun savePoints(points: Float) {
        sharedPrefs.edit().putFloat("points", points).apply()
    }

    fun getPoints(): Float {
        return sharedPrefs.getFloat("points", 0f)
    }

    fun saveAmount(amount: String) {
        val amountList = getAmounts()
        amountList.add(amount)
        val amountJson = gson.toJson(amountList)
        sharedPrefs.edit().putString("amountList", amountJson).apply()
    }

    fun getAmounts(): MutableList<String> {
        val amountJson = sharedPrefs.getString("amountList", null)
        return if (!amountJson.isNullOrBlank()) {
            gson.fromJson(amountJson, object : TypeToken<List<String>>() {}.type)
        } else {
            mutableListOf()
        }
    }

    fun removeAmount(position: Int) {
        val amountList = getAmounts()
        if (position >= 0 && position < amountList.size) {
            amountList.removeAt(position)
            val amountJson = gson.toJson(amountList)
            sharedPrefs.edit().putString("amountList", amountJson).apply()
        }
    }


}











