package com.juarezcode.artbooktesting.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "arts")
data class Art(
    @PrimaryKey(autoGenerate = true)
    val name: String,
    val artisName: String,
    val year: Int,
    val imageUrl: String,
    val id: Int? = null,
)
