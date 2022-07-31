package com.fyl.book.data

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsRepository @Inject constructor(
    private val sharedPrefsHelper: SharedPrefsHelper
) {

}

@Singleton
class SharedPrefsHelper @Inject constructor(
    @ApplicationContext private val context: Context
) : SharedPreferences, SharedPreferences.Editor {

    private val name = "book"
    private var sharedPrefs =  context.getSharedPreferences(name, Context.MODE_PRIVATE)
    private var edit = sharedPrefs.edit()

    override fun getAll(): MutableMap<String, *> {
        return sharedPrefs.all
    }

    override fun getString(key: String?, defValue: String?): String? {
        return sharedPrefs.getString(key, defValue)
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
        return sharedPrefs.getStringSet(key, defValues)
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return sharedPrefs.getInt(key, defValue)
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return sharedPrefs.getLong(key, defValue)
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return sharedPrefs.getFloat(key, defValue)
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defValue)
    }

    override fun contains(key: String?): Boolean {
        return sharedPrefs.contains(key)
    }

    override fun edit(): SharedPreferences.Editor {
        return edit
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        sharedPrefs.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener?) {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun putString(key: String?, value: String?): SharedPreferences.Editor {
        return edit.putString(key, value)
    }

    override fun putStringSet(key: String?, values: MutableSet<String>?): SharedPreferences.Editor {
        return edit.putStringSet(key, values)
    }

    override fun putInt(key: String?, value: Int): SharedPreferences.Editor {
        return edit.putInt(key, value)
    }

    override fun putLong(key: String?, value: Long): SharedPreferences.Editor {
        return edit.putLong(key, value)
    }

    override fun putFloat(key: String?, value: Float): SharedPreferences.Editor {
        return edit.putFloat(key, value)
    }

    override fun putBoolean(key: String?, value: Boolean): SharedPreferences.Editor {
        return edit.putBoolean(key, value)
    }

    override fun remove(key: String?): SharedPreferences.Editor {
        return edit.remove(key)
    }

    override fun clear(): SharedPreferences.Editor {
        return edit.clear()
    }

    override fun commit(): Boolean {
        return edit.commit()
    }

    override fun apply() {
        return edit.apply()
    }
}