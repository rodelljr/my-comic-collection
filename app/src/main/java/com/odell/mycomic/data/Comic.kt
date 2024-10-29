package com.odell.mycomic.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comics")
data class Comic (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 100,
    val name: String,
    val issue: Int,
    val publisher: String
)