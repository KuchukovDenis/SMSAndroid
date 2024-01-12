package com.example.readingsmsapp.data

data class SmsItem(
    val sender: String,
    val massages: List<MassageItem>
)