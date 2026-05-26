package com.example.lazylist.data.repository

import com.example.lazylist.domain.model.Item

class ItemRepoImplement {
    fun getItems(): List<Item> {
        return listOf(
            Item(1, "Item Pertama", "Deskripsi item kesatu"),
            Item(2, "Item Kedua", "Deskripsi item kedua"),
            Item(3, "Item Ketiga", "Deskripsi item ketiga"),
            Item(4, "Item Keempat", "Deskripsi item keempat"),
            Item(5, "Item Kelima", "Deskripsi item kelima"),
            Item(6, "Item Keenam", "Deskripsi item keenam"),
            Item(7, "Item Ketujuh", "Deskripsi item ketujuh"),
            Item(8, "Item Kedelapan", "Deskripsi item kedelapan"),
            Item(9, "Item Kesembilan", "Deskripsi item kesembilan"),
            Item(10, "Item Kesepuluh", "Deskripsi item kesepuluh")
        )
    }
}
