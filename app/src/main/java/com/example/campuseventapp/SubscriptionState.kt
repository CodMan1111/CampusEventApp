package com.example.campuseventapp

import android.content.Context

object SubscriptionState {
    private const val PREFS_NAME = "subscriptions"

    private lateinit var context: Context
    val subscribedCategories = mutableSetOf<String>()

    fun init(context: Context) {
        this.context = context.applicationContext
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val saved = prefs.getStringSet("categories", emptySet()) ?: emptySet()
        subscribedCategories.clear()
        subscribedCategories.addAll(saved)
    }

    fun toggle(category: String) {
        if (subscribedCategories.contains(category)) {
            subscribedCategories.remove(category)
        } else {
            subscribedCategories.add(category)
        }
        save()
    }

    fun isSubscribed(category: String): Boolean {
        return subscribedCategories.contains(category)
    }

    private fun save() {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putStringSet("categories", subscribedCategories).apply()
    }
}