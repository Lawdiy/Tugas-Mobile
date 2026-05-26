package com.example.recyclerview.data.repository

import com.example.recyclerview.R
import com.example.recyclerview.domain.model.Item

class ItemRepoImplement {
    fun getItems(): List<Item> {
        return listOf(
            Item(1, "Item Pertama", "Deskripsi item kesatu", R.drawable.ic_launcher_foreground),
            Item(2, "Item Kedua", "Deskripsi item kedua", R.drawable.ic_launcher_foreground),
            Item(3, "Item Ketiga", "Deskripsi item ketiga", R.drawable.ic_launcher_foreground),
            Item(4, "Item Keempat", "Deskripsi item keempat", R.drawable.ic_launcher_foreground),
            Item(5, "Item Kelima", "Deskripsi item kelima", R.drawable.ic_launcher_foreground),
            Item(6, "Item Keenam", "Deskripsi item Keenam", R.drawable.ic_launcher_foreground),
            Item(7, "Item Ketujuh", "Deskripsi item Ketujuh", R.drawable.ic_launcher_foreground),
            Item(8, "Item Kedelapan", "Deskripsi item Kedelapan", R.drawable.ic_launcher_foreground),
            Item(9, "Item Kesembilan", "Deskripsi item Kesembilan", R.drawable.ic_launcher_foreground),
            Item(10, "Item Kesepuluh", "Deskripsi item Kesepuluh", R.drawable.ic_launcher_foreground)
        )
    }
}
