package com.example.recyclerview.domain.model

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val imageResId: Int,
    var isSwitchOn: Boolean = false
)
