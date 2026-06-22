package com.example.manhwalist.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "read_status")
data class ReadStatus(
    @PrimaryKey(autoGenerate = true)
    val statusId: Int = 0,
    val statusName: String
)

@Entity(
    tableName = "manhwa",
    foreignKeys = [
        ForeignKey(
            entity = ReadStatus::class,
            parentColumns = ["statusId"],
            childColumns = ["statusId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Manhwa(
    @PrimaryKey(autoGenerate = true)
    val manhwaId: Int = 0,
    val title: String,
    val author: String,
    val synopsis: String,
    val coverImageUrl: String,
    val currentChapter: Int,
    val statusId: Int
)

data class ManhwaWithStatus(
    val manhwaId: Int,
    val title: String,
    val author: String,
    val synopsis: String,
    val coverImageUrl: String,
    val currentChapter: Int,
    val statusId: Int,
    val statusName: String
)
