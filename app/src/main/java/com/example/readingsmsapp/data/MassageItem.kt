package com.example.readingsmsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MassageItem(
    val type: Int,
    val text: String,
    val date: Long
) : Parcelable