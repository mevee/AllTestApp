package com.pareminder.data.local_db.tables

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val title: String?,
    val message: String?,
    val time: String,
) : Serializable