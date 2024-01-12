package com.example.readingsmsapp.presenter.sms_list

import android.content.ContentResolver
import android.provider.Telephony
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readingsmsapp.data.MassageItem
import com.example.readingsmsapp.data.SmsItem

class SmsListViewModel : ViewModel() {

    private val _smsList = MutableLiveData<List<SmsItem>>()
    val smsList: LiveData<List<SmsItem>>
        get() = _smsList

    fun loadSmsList(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val result = AddressAndMessageList(mutableListOf())

        if (cursor?.moveToFirst() == true) {
            do {
                val address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS))
                val type = cursor.getInt(cursor.getColumnIndexOrThrow(Telephony.Sms.TYPE))
                val date = cursor.getLong(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE))
                val body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY))

                result.list.add(address to MassageItem(type, body, date))

            } while (cursor.moveToNext())
        }

        cursor?.close()
        _smsList.postValue(result.toSmsChatEntriesList())
    }
}

@JvmInline
value class AddressAndMessageList(
    val list: MutableList<Pair<String, MassageItem>>
) {
    fun toSmsChatEntriesList(): List<SmsItem> =
        list
            .groupBy { it.first }
            .map {
                val messages = it.value.map { it.second }
                SmsItem(it.key, messages)
            }
}
