package com.example.lazylist.domain.model

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val isSwitchOn: Boolean = false
)
