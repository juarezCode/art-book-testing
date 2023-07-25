package com.juarezcode.artbooktesting.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name: String,
    val artisName: String,
    val year: Int,
    val imageUrl: String
)
